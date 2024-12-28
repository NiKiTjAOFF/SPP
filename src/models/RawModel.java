package models;

//Представление 3д модели в памяти
public class RawModel {
    private int vaoID;//Уникальный ID для Vertex Array Object
    private int vertexCount;//Количество вершин в 3д модели
    public RawModel(int vaoID, int vertexCount){
        this.vaoID = vaoID;
        this.vertexCount = vertexCount;
    }
    public int getVaoID() {
        return vaoID;
    }
    public int getVertexCount() {
        return vertexCount;
    }
}
