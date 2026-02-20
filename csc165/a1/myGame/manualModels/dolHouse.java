package myGame.manualModels;

import tage.Utils;
import tage.shapes.ManualObject;

public class dolHouse extends ManualObject {
    private float[] vertices = new float[]
            {
                    3.0f,2.5f,2.0f,     -3.0f,2.5f,2.0f,     3.0f,0.0f,2.0f,     // front wall
                    3.0f,0.0f,2.0f,     -3.0f,2.5f,2.0f,    -3.0f,0.0f,2.0f,
                    -3.0f,2.5f,-2.0f,    3.0f,2.5f,-2.0f,   -3.0f,0.0f,-2.0f,    // back wall
                    -3.0f,0.0f,-2.0f,    3.0f,2.5f,-2.0f,    3.0f,0.0f,-2.0f,
                    -3.0f,0.0f,2.0f,     3.0f,0.0f,2.0f,    -3.0f,0.0f,-2.0f,    // floor
                    -3.0f,0.0f,-2.0f,    3.0f,0.0f,2.0f,     3.0f,0.0f,-2.0f,
                    -3.5f,4.0f,0.0f,     3.5f,4.0f,0.0f,     3.5f,2.3f,2.5f,     // roof - slope
                    -3.5f,4.0f,0.0f,     3.5f,2.3f,2.5f,    -3.5f,2.3f,2.5f,
                    -3.5f,4.0f,0.0f,     3.5f,4.0f,0.0f,     3.5f,2.3f,-2.5f,
                    -3.5f,4.0f,0.0f,     3.5f,2.3f,-2.5f,   -3.5f,2.3f,-2.5f
            };
    private float[] texcoords = new float[]
            {
                    1.0f,1.0f,          0.0f,1.0f,          1.0f,0.0f,           // front
                    1.0f,0.0f,          0.0f,1.0f,          0.0f,0.0f,
                    0.0f,1.0f,          1.0f,1.0f,          0.0f,0.0f,           // back
                    0.0f,0.0f,          1.0f,1.0f,          1.0f,0.0f,
                    0.0f,0.0f,          1.0f,0.0f,          0.0f,1.0f,           // floor
                    0.0f,1.0f,          1.0f,0.0f,          1.0f,1.0f,
                    0.0f,0.0f,          1.0f,0.0f,          1.0f,1.0f,           // roof - slope
                    0.0f,0.0f,          0.0f,1.0f,          0.0f,1.0f,
                    0.0f,0.0f,          1.0f,0.0f,          1.0f,1.0f,
                    0.0f,0.0f,          1.0f,1.0f,          0.0f,1.0f
            };
    private float[] normals = new float[]
            {
                    0.0f,0.0f,1.0f,     0.0f,0.0f,1.0f,     0.0f,0.0f,1.0f,      // front
                    0.0f,0.0f,1.0f,     0.0f,0.0f,1.0f,     0.0f,0.0f,1.0f,
                    0.0f,0.0f,-1.0f,    0.0f,0.0f,-1.0f,    0.0f,0.0f,-1.0f,     // back
                    0.0f,0.0f,-1.0f,    0.0f,0.0f,-1.0f,    0.0f,0.0f,-1.0f,
                    0.0f,1.0f,0.0f,     0.0f,1.0f,0.0f,     0.0f,1.0f,0.0f,      // floor
                    0.0f,1.0f,0.0f,     0.0f,1.0f,0.0f,     0.0f,1.0f,0.0f,
                    0.0f,-0.8f,-0.55f,  0.0f,-0.8f,-0.55f, 0.0f,-0.8f,-0.55f,    // roof
                    0.0f,-0.8f,-0.55f,  0.0f,-0.8f,-0.55f, 0.0f,-0.8f,-0.55f,
                    0.0f,-0.8f,-0.55f,  0.0f,-0.8f,-0.55f, 0.0f,-0.8f,-0.55f,
                    0.0f,-0.8f,-0.55f,  0.0f,-0.8f,-0.55f, 0.0f,-0.8f,-0.55f,

            };


    public dolHouse() {
        super();
        setNumVertices(90);
        setVertices(vertices);
        setTexCoords(texcoords);
        setNormals(normals);
        setMatAmb(Utils.defAmbient());
        setMatDif(Utils.defDiffuse());
        setMatSpe(Utils.defSpecular());
        setMatShi(Utils.defShininess());
    }
}
