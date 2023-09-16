package org.dbnoobs.noobdb.memorybackend;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class MemoryCell implements Cell{
    private byte[] data;

    public MemoryCell() {
    }

    public MemoryCell(byte[] data) {
        this.data = data;
    }

    public MemoryCell(int data) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(Integer.BYTES);
        byteBuffer.putInt(data);
        byteBuffer.rewind();
        this.data = byteBuffer.array();
    }

    public MemoryCell(String data) {
        this.data = data.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String asText() {
        return new String(data);
    }

    @Override
    public int asInt() {
        return ByteBuffer.wrap(data).getInt();
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemoryCell that = (MemoryCell) o;
        return Arrays.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(data);
    }

    @Override
    public String toString() {
        return "MemoryCell{" +
                "data=" + Arrays.toString(data) +
                '}';
    }
}
