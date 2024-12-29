#version 400 core

in vec2 position;

out vec4 vColor;

uniform mat4 projectionMatrix;
uniform mat4 modelViewMatrix;
uniform float lifePercentage;
uniform vec3 startColor;
uniform vec3 endColor;

void main(void){
    gl_Position = projectionMatrix * modelViewMatrix * vec4(position, 0.0, 1.0);
    vColor = vec4(startColor + (endColor - startColor) * lifePercentage, 1);
}