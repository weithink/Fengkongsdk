package com.weithink.fengkong.network;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.Deflater;
import java.util.zip.GZIPOutputStream;

public class MyGZIPOutputStream extends GZIPOutputStream {


    public MyGZIPOutputStream(OutputStream out) throws IOException {
        super(out);
        setLevel();
    }

    public void setLevel() {
        def.setLevel(Deflater.BEST_COMPRESSION);
    }
}
