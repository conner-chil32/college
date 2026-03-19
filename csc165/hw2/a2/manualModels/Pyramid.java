package a2.manualModels;

import tage.Utils;
import tage.shapes.ManualObject;

public class Pyramid extends ManualObject {
    private float[] vertices = new float[] {
            // Front face
            0.0f,  1.0f,  0.0f,
            -1.0f, -1.0f,  1.0f,
            1.0f, -1.0f,  1.0f,

            // Right face
            0.0f,  1.0f,  0.0f,
            1.0f, -1.0f,  1.0f,
            1.0f, -1.0f, -1.0f,

            // Back face
            0.0f,  1.0f,  0.0f,
            1.0f, -1.0f, -1.0f,
            -1.0f, -1.0f, -1.0f,

            // Left face
            0.0f,  1.0f,  0.0f,
            -1.0f, -1.0f, -1.0f,
            -1.0f, -1.0f,  1.0f,

            // Base triangle 1
            -1.0f, -1.0f,  1.0f,
            -1.0f, -1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,

            // Base triangle 2
            -1.0f, -1.0f,  1.0f,
            1.0f, -1.0f, -1.0f,
            1.0f, -1.0f,  1.0f

    };

    private float[] texcoords = new float[] {
            // Front
            0.5f, 1.0f,
            0.0f, 0.0f,
            1.0f, 0.0f,

            // Right
            0.5f, 1.0f,
            0.0f, 0.0f,
            1.0f, 0.0f,

            // Back
            0.5f, 1.0f,
            0.0f, 0.0f,
            1.0f, 0.0f,

            // Left
            0.5f, 1.0f,
            0.0f, 0.0f,
            1.0f, 0.0f,

            // Base tri 1
            0.0f, 1.0f,
            0.0f, 0.0f,
            1.0f, 0.0f,

            // Base tri 2
            0.0f, 1.0f,
            1.0f, 0.0f,
            1.0f, 1.0f
    };

    private float[] normals = new float[] {
            // Front
            0.0f,  0.447f,  0.894f,
            0.0f,  0.447f,  0.894f,
            0.0f,  0.447f,  0.894f,

            // Right
            0.894f, 0.447f, 0.0f,
            0.894f, 0.447f, 0.0f,
            0.894f, 0.447f, 0.0f,

            // Back
            0.0f, 0.447f, -0.894f,
            0.0f, 0.447f, -0.894f,
            0.0f, 0.447f, -0.894f,

            // Left
            -0.894f, 0.447f, 0.0f,
            -0.894f, 0.447f, 0.0f,
            -0.894f, 0.447f, 0.0f,

            // Base
            0.0f, -1.0f, 0.0f,
            0.0f, -1.0f, 0.0f,
            0.0f, -1.0f, 0.0f,

            0.0f, -1.0f, 0.0f,
            0.0f, -1.0f, 0.0f,
            0.0f, -1.0f, 0.0f
    };

    public Pyramid() {
        super();
        setNumVertices(18);
        setVertices(vertices);
        setTexCoords(texcoords);
        setNormals(normals);
        setMatAmb(Utils.defAmbient());
        setMatDif(Utils.defDiffuse());
        setMatSpe(Utils.defSpecular());
        setMatShi(Utils.defShininess());
    }
}
