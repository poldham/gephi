/*
Copyright 2008-2011 Gephi
Authors : Antonio Patriarca <antoniopatriarca@gmail.com>
Website : http://www.gephi.org

This file is part of Gephi.

Gephi is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

Gephi is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with Gephi.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.gephi.visualization.rendering.apiimpl.command.node;

import com.jogamp.common.nio.Buffers;
import java.nio.IntBuffer;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import org.gephi.graph.api.NodeShape;
import org.gephi.visualization.data.graph.VizNode2D;
import org.gephi.visualization.rendering.apiimpl.command.node.texture.Node2DTextureBuilder;
import org.gephi.visualization.rendering.camera.Camera;
import org.gephi.visualization.rendering.camera.OrthoCamera;
import org.gephi.visualization.rendering.camera.RenderArea;
import org.gephi.visualization.rendering.command.buffer.Buffer;
import org.gephi.visualization.rendering.command.buffer.BufferedTechnique;

/**
 *
 * @author Antonio Patriarca <antoniopatriarca@gmail.com>
 */
public final class Shape2DTechniqueGL12 extends BufferedTechnique<VizNode2D> {
    private final int fillTex;
    private final int borderTex;
    
    public Shape2DTechniqueGL12(GL gl, Buffer.Type type, NodeShape shape) {
        super(new Shape2DLayoutGL2(), type);
        
        int size;
        {
            final IntBuffer buffer = Buffers.newDirectIntBuffer(1); 
            gl.glGetIntegerv(GL.GL_MAX_TEXTURE_SIZE, buffer);
            size = buffer.get(0) > 512 ? 512 : buffer.get(0);
        }
        ((Shape2DLayoutGL2) this.layout()).texBorderSize(2.0f / size);
        
        this.fillTex = Node2DTextureBuilder.createFillTexture(gl, shape, size);
        this.borderTex = Node2DTextureBuilder.createBolderTexture(gl, shape, size, 0.2f);
    }

    @Override
    public void end(GL gl) {
        super.end(gl);
        
        GL2 gl2 = gl.getGL2();
        if (gl2 == null) return;
        
        gl2.glMatrixMode(GL2.GL_PROJECTION);
        gl2.glLoadIdentity();
        
        gl2.glMatrixMode(GL2.GL_MODELVIEW);
        gl2.glLoadIdentity();
        
        gl2.glBindTexture(GL2.GL_TEXTURE_2D, 0);
        gl2.glDisablei(GL2.GL_BLEND, 0);
        
        gl.glDisable(GL.GL_TEXTURE_2D);
        
        gl.glDisable(GL.GL_DEPTH_TEST);
    }

    @Override
    public void dispose(GL gl) {
        IntBuffer textures = Buffers.newDirectIntBuffer(2);
        textures.put(this.fillTex);
        textures.put(this.borderTex);
        textures.rewind();
        gl.glDeleteTextures(2, textures);
    }

    private void setPass0(GL2 gl) {
        gl.glBindTexture(GL2.GL_TEXTURE_2D, this.fillTex);
        
        gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE);
        
        gl.glEnable(GL2.GL_ALPHA_TEST);
        
        gl.glAlphaFunc(GL2.GL_GREATER, 0.4f);
    }

    private void setPass1(GL2 gl) {
        gl.glBindTexture(GL2.GL_TEXTURE_2D, this.borderTex);
        
        gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE);
        
        gl.glDisable(GL2.GL_ALPHA_TEST);
        gl.glEnablei(GL2.GL_BLEND, 0);
        
        gl.glBlendEquationSeparate(GL2.GL_FUNC_ADD, GL2.GL_FUNC_ADD);
        gl.glBlendFuncSeparate(GL2.GL_ONE, GL2.GL_ONE_MINUS_SRC_ALPHA, GL.GL_ONE, GL.GL_ZERO);
        
    }

    @Override
    public boolean begin(GL gl, Camera camera, RenderArea renderArea) {        
        gl.glEnable(GL.GL_TEXTURE_2D);
        
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LEQUAL);
        
        return super.begin(gl, camera, renderArea);
    }

    @Override
    public boolean advanceToNextPass(GL gl) {
        GL2 gl2 = gl.getGL2();
        if (gl2 == null) return false;
        
        boolean result = super.advanceToNextPass(gl);
        
        switch (this.currentPass) {
            case 0:
                setPass0(gl2);
                return result;
            case 1:
                setPass1(gl2);
                return result;
            default:
                return false;
        }
    }

    @Override
    protected boolean setCamera(GL gl, Camera camera, RenderArea renderArea) {
        GL2 gl2 = gl.getGL2();
        if (gl2 == null || !(camera instanceof OrthoCamera)) return false;
        
        gl2.glMatrixMode(GL2.GL_PROJECTION);
        
        gl2.glLoadMatrixf(camera.projMatrix(renderArea).toArray(), 0);
        
        gl2.glMatrixMode(GL2.GL_MODELVIEW);
        
        gl2.glLoadMatrixf(camera.viewMatrix(renderArea).toArray(), 0);
        
        return true;
    }
}