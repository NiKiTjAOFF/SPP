#version 400 core

in vec2 position;

out vec4 vColor;

uniform mat4 projectionMatrix;
uniform mat4 modelViewMatrix;
uniform float lifePercentage;
uniform vec3 colors[10];
uniform int colorsLength;

void main(void){
    gl_Position = projectionMatrix * modelViewMatrix * vec4(position, 0.0, 1.0);
    float colorChangePercentage = 1.0 / float(colorsLength);//Порог. Прошёл его - взял два новых цвета для интерполяции
    int currentColor = int(floor(lifePercentage / colorChangePercentage));//Текущая пара цветов для интерполяции
    float currentColorPercentage = lifePercentage / colorChangePercentage - float(currentColor);//Процент интерполяции между текущими двумя цветами
    vColor = vec4((colors[currentColor + 1] - colors[currentColor]) * currentColorPercentage + colors[currentColor], 1.0);
}