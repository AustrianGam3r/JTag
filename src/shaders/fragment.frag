#version 450 core


uniform sampler2D tex_object;
out vec3 color;

in VS_OUT
{
	vec2 tc;
} fs_in;

void main()
{
    color = texture(tex_object,fs_in.tc).rgb;
}