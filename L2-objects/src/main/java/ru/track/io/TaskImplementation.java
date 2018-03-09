package ru.track.io;

import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.track.io.vendor.Bootstrapper;
import ru.track.io.vendor.FileEncoder;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public final class TaskImplementation implements FileEncoder {

    /**
     * @param finPath  where to read binary data from
     * @param foutPath where to write encoded data. if null, please create and use temporary file.
     * @return file to read encoded data from
     * @throws IOException is case of input/output errors
     */
    @NotNull
    public File encodeFile(@NotNull String finPath, @Nullable String foutPath) throws IOException {

        /* XXX: https://docs.oracle.com/javase/8/docs/api/java/io/File.html#deleteOnExit-- */
//        throw new UnsupportedOperationException(); // TODO: implement

        File fin = new File(finPath);
        File fout;

        if (foutPath != null) {
            fout = new File(foutPath);
        } else {
            fout = File.createTempFile("temp_file", ".txt");
            fout.deleteOnExit();
        }

        InputStream inputStream = new FileInputStream(fin);
        byte[] bytesIn = IOUtils.toByteArray(inputStream);
        List<String> bytes = new ArrayList<>();
        int tail = bytesIn.length % 3;

        for (int i = 0; i < bytesIn.length - tail; i += 3) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(byteToString(bytesIn[i]));
            stringBuilder.append(byteToString(bytesIn[i + 1]));
            stringBuilder.append(byteToString(bytesIn[i + 2]));

            String str;

            int fir = Integer.parseInt(stringBuilder.substring(0, 6), 2);
            int sec = Integer.parseInt(stringBuilder.substring(6, 12), 2);
            int thi = Integer.parseInt(stringBuilder.substring(12, 18), 2);
            int fou = Integer.parseInt(stringBuilder.substring(18, 24), 2);
            str = String.valueOf(toBase64[fir]) +
                    String.valueOf(toBase64[sec]) +
                    String.valueOf(toBase64[thi]) +
                    String.valueOf(toBase64[fou]);

            bytes.add(str);
        }

        if (tail == 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(byteToString(bytesIn[bytesIn.length - 1]));
            stringBuilder.append("0000");

            String str;

            int fir = Integer.parseInt(stringBuilder.substring(0, 6), 2);
            int sec = Integer.parseInt(stringBuilder.substring(6, 12), 2);
            str = String.valueOf(toBase64[fir]) +
                    String.valueOf(toBase64[sec]) +
                    "==";

            bytes.add(str);
        }

        if (tail == 2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(byteToString(bytesIn[bytesIn.length - 2]));
            stringBuilder.append(byteToString(bytesIn[bytesIn.length - 1]));
            stringBuilder.append("00");

            String str;

            int fir = Integer.parseInt(stringBuilder.substring(0, 6), 2);
            int sec = Integer.parseInt(stringBuilder.substring(6, 12), 2);
            int thi = Integer.parseInt(stringBuilder.substring(12, 18), 2);
            str = String.valueOf(toBase64[fir]) +
                    String.valueOf(toBase64[sec]) +
                    String.valueOf(toBase64[thi]) +
                    "=";

            bytes.add(str);
        }


        FileWriter fw = new FileWriter(fout);
        for (int i = 0; i < bytes.size(); i++) {
            fw.write(bytes.get(i));
        }
        fw.close();

        return fout;
    }

    private String byteToString(byte currentByte) {
        return String.format("%8s", Integer.toBinaryString(currentByte & 0xFF)).replace(' ', '0');
    }

    private static final char[] toBase64 = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'
    };

    public static void main(String[] args) throws Exception {
        final FileEncoder encoder = new TaskImplementation();
        // NOTE: open http://localhost:9000/ in your web browser
        (new Bootstrapper(args, encoder))
                .bootstrap("", new InetSocketAddress("127.0.0.1", 9000));
    }
}
