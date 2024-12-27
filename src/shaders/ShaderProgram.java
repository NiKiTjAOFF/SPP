package shaders;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

//Содержит все поля и методы для люого шейдера
public abstract class ShaderProgram {
    private int programID;
    private int vertexShaderID;
    private int fragmentShaderID;

    public ShaderProgram(String vertexFile, String fragmentFile) {
        vertexShaderID = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);//Загрузка вершинного шейдера
        fragmentShaderID = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);//Загрузка фрагментарного шейдера
        programID = GL20.glCreateProgram();//Создание программы
        GL20.glAttachShader(programID, vertexShaderID);//Добавление вершинного шейдера к программе
        GL20.glAttachShader(programID, fragmentShaderID);//Добавление фрагментарного шейдера к программе
        GL20.glLinkProgram(programID);//Линковка программы
        GL20.glValidateProgram(programID);//Валидация программы
        bindAttributes();
    }

    //Начать использование программы
    public void start() {
        GL20.glUseProgram(programID);
    }

    //Заакончить использование программы
    public void stop() {
        GL20.glUseProgram(0);
    }

    //Удалить программу
    public void cleanUp() {
        stop();
        GL20.glDetachShader(programID, vertexShaderID);//Отвязка шейдеров
        GL20.glDetachShader(programID, fragmentShaderID);
        GL20.glDeleteShader(vertexShaderID);//Удаление шейдеров
        GL20.glDeleteShader(fragmentShaderID);
        GL20.glDeleteProgram(programID);//Удаление программы
    }

    //Связывание атрибутов с программой
    protected void bindAttribute(int attribute, String variableName) {
        GL20.glBindAttribLocation(programID, attribute, variableName);
    }

    //Реализация сопоставления атрибутов с шейдерами
    protected abstract void bindAttributes();

    //Загрузчик шейдера, где int - тип шейдера
    private static int loadShader(String file, int type){
        StringBuilder shaderSource = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while((line = reader.readLine()) != null) {
                shaderSource.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Could not read file!");
            e.printStackTrace();
            System.exit(-1);
        }
        int shaderID = GL20.glCreateShader(type);//Создание шейдер-программы
        GL20.glShaderSource(shaderID, shaderSource);//Сопоставление кода шейдера с данным ID
        GL20.glCompileShader(shaderID);//Компиляция шейдера
        if(GL20.glGetShader(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {//Проверка правильности компиляции шейдера
            System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
            System.err.println("Could not compile shader.");
            System.exit(-1);
        }
        return shaderID;
    }
}
