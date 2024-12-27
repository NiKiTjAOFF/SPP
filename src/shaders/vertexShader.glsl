#version 400 core//версия glsl

//Входные данные
in vec3 position;//координаты вершины
in vec2 textureCoords;//Координаты текстур для вершины

//Выходные данные
//out vec3 color;//Цвет вершины rgb
out vec2 outTextureCoords;//Координата текстуры вершины uv

void main(void) {
    gl_Position = vec4(position, 1.0);//Отображение вершины
    //color = vec3(position.x+0.5, 1.0, position.y + 0.5);
    outTextureCoords = textureCoords;
}