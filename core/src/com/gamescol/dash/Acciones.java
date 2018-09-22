package com.gamescol.dash;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;

public class Acciones 
{
	//==funciones para mover cosas con respecto a la camara :D=============//
	public static float movercosasx(float numero, Camera cam)
	{
		Vector3 touchPos = new Vector3();
	      touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
	      cam.unproject(touchPos);
	      
	      return touchPos.x-numero/2;
	}
	
	public static float movercosasy(float numero, Camera cam)
	{
		Vector3 touchPos = new Vector3();
	      touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
	      cam.unproject(touchPos);
	      
	      return touchPos.y-numero/2;
	}
	
	
	//===============================
	public static float porcenposwitdth(float num)
	{
		float d;
		d=Gdx.graphics.getWidth()-(Gdx.graphics.getWidth()*num/100);
		
		return d;
	}
	public static float porcenposheight(float num)
	{
		float d;
		d=Gdx.graphics.getHeight()-(Gdx.graphics.getHeight()*num/100);
		
		return d;
	}
	
	public int subirvelocidad(int punto, int velocidad)
	{
		
		if(velocidad<20)
		{
			velocidad=velocidad;
		}
		if(punto>19 && punto<21)
		{
			velocidad=12;
		}
		else if(punto>50 && punto<52)
		{
			velocidad=14;
		}
		else if(punto>104 && punto<106)
		{
			velocidad=16;
		}
		
		return velocidad;
	}
	
	public int masnivel(int puntos, int nivel)
	{
		if(puntos>20 && puntos<39)
		{
			nivel=1;
		}
		else if(puntos>50 && puntos<53)
		{
			nivel=2;
		}
		else if(puntos>140 && puntos<149)
		{
			nivel=3;
		}
		else if(puntos>189 && puntos<191)
		{
			nivel=4;
		}
		else if(puntos>240 && puntos<242)
		{
			nivel=5;
		}
		/*else if(puntos>639 && puntos<1279)
		{
			nivel=7;
		}
		else if(puntos>1279 && puntos<2559)
		{
			nivel=8;
		}
		else if(puntos>2559 && puntos<5119)
		{
			nivel=9;
		}
		else if(puntos>5119 && puntos<10239)
		{
			nivel=10;
		}
		else if(puntos>-1 && puntos<19)
		{
			nivel=1;
		}*/
		
		return nivel;
	}

}
