#version 400 core

//in vec3 color;
in vec2 outTextureCoords;

out vec4 outColor;

uniform sampler2D textureSampler;

void main(void) {
    //outColor = vec4(color, 1.0);
    outColor = texture(textureSampler, outTextureCoords);
}