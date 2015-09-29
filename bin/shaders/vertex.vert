#version 450 core



layout (location = 0) in vec4 position;
layout(location = 4) in vec2 vertexUV;

float a[] = float[](0,1,1,0,1,0, 1,1 , 0,1, 1,0);
out VS_OUT
{
	vec2 tc;
} vs_out;

layout(location = 1) uniform mat4 mv_matrix;
layout(location = 2) uniform mat4 proj_matrix;

void main()
{
	vs_out.tc= vec2(a[gl_VertexID],a[gl_VertexID + 1]);
    gl_Position = proj_matrix * mv_matrix * position;
}