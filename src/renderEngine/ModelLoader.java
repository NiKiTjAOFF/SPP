package renderEngine;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

//Загрузчик 3д модели в память
public class ModelLoader {
    private List<Integer> vaos = new ArrayList<>();//Хранилище VAO
    private List<Integer> vbos = new ArrayList<>();//Хранилище VBO
    private static final int VERTEX_DATA_AMOUNT = 3;

    //Получает позиции вершин 3д модели, состоящих из 3 координат: x, y, z
    public RawModel loadToVAO(float[] positions){
        int vaoID = createVAO();
        storeDataInAttributeList(0, positions);
        unbindVAO();
        return new RawModel(vaoID, positions.length / VERTEX_DATA_AMOUNT);
    }

    //Очистить все VBO и VAO
    public void cleanUp(){
        for(int vao:vaos){
            GL30.glDeleteVertexArrays(vao);//Удаление VAO
        }
        for(int vbo:vbos){
            GL15.glDeleteBuffers(vbo);//Удаление VBO
        }
    }

    //Создание нового VAO
    private int createVAO(){
        int vaoID = GL30.glGenVertexArrays();//Создание пустого VAO
        vaos.add(vaoID);
        GL30.glBindVertexArray(vaoID);//Активация данного VAO
        return vaoID;
    }

    //Добавление атрибута в VAO
    private void storeDataInAttributeList(int attributeNumber, float[] data){
        int vboID = GL15.glGenBuffers();//Создание хранилища данных VBO
        vbos.add(vboID);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);//Активация VBO, типа ARRAY_BUFFER
        FloatBuffer buffer = storeDataInFloatBuffer(data);//Преобразование массива в FloatBuffer
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);//Заполнение VBO типом ARRAY_BUFFER и указываем, что данные не меняются больше

        //Добавление данного VBO в атрибут VAO под указанным номером attributeNumber;
        //размером данных, описывающих данный атрибут VERTEX_DATA_AMOUNT;
        //тип данных - GL_FLOAT;
        //в нашем случае данные не нормализованы - false;
        //в нашем буфере нет дополнительных атрибутов, пропускать следующее количество нет необходимотси - 0
        //в нашем буфере данные начинаются с начала массива, отступать нет необходимости - 0
        GL20.glVertexAttribPointer(attributeNumber, VERTEX_DATA_AMOUNT, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);//Деактивация текущего VBO
    }

    //Удаление VAO
    private void unbindVAO(){
        GL30.glBindVertexArray(0);//Деактивация текущего VAO
    }

    //Преобразование массива координат в тип FloatBuffer
    private FloatBuffer storeDataInFloatBuffer(float[] data){
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);//Создание FloatBuffer
        buffer.put(data);//Заполнение данными
        buffer.flip();//Изменение состояние буфера на чтение с изменения
        return buffer;
    }
}
