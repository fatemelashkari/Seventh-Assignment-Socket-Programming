package Server;
public class MyFiles {
    private int id;
    private String name;
    private byte[] data;
    private String file;

    public MyFiles(int id , String name , byte[] data , String file) {
        this.id = id;
        this.name = name;
        this.data = data;
        this.file = file;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public byte[] getData() {
        return data;
    }

    public String getFile() {
        return file;
    }
}
