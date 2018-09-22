package com.gamescol.dash;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.util.Random;

public class ability extends ApplicationAdapter {
	SpriteBatch batch;
	Texture obstaculos, personajes, barras, play, coins, barratras, tablero, fondos, logo;
	TextureRegion[][] personaje, obstaculo, coin, fondo, barra;
	TextureRegion[] indice;
	TextureRegion animacionlol;
	Random random;
	Rectangle[] rectangleobstaba, rectangleobstari, recsupba, recsupri, rectanglecoins;
	Rectangle rectangleguia, recpersonaje, rectangleplay; /*recobstamedio*/
	BitmapFont bitmap;
	String puntost;
	Acciones acciones;
	Animation animacion;
	Music[] musica;
	Sound sonido;
	Preferences preferencias;
	OrthographicCamera camara;
	
	Vector3 touchposbarra;
	//velocidad maxima 14
	int puntosguarda=0, nivel=0;
	int velocidad=10, posyperso=0, subir=0, bajar=1, cantidadobsta=3, altobarra=520, puntos=0, perdio=0, pantalla=1, canditadcoins=2, idmundo=2, idpersonaje=1; //posxobstamedio=1000
	float tiempo=0, tiempo1=0;
	int[] posxobstaba, posxobstari, posxcoin, posycoin;
	
	//private AdsController adsController;
    
	  /*public ability(AdsController adsController){
	        this.adsController = adsController;
	    }*/
	
	@Override
	public void create () {
		touchposbarra=new Vector3();
		//musica
		musica=new Music[3];
		musica[0]=Gdx.audio.newMusic(Gdx.files.getFileHandle("musica/Lagrima.mp3", File.FileType.Internal));
		musica[1]=Gdx.audio.newMusic(Gdx.files.getFileHandle("musica/The Old Song.mp3", FileType.Internal));
		musica[2]=Gdx.audio.newMusic(Gdx.files.getFileHandle("musica/endless orgy.mp3", FileType.Internal));
		sonido=Gdx.audio.newSound(Gdx.files.getFileHandle("sonidos/SFX_Jump_31_0.ogg", FileType.Internal));
		
		//datos guardados
		preferencias = Gdx.app.getPreferences("datos de ability");
		puntosguarda=preferencias.getInteger("puntos");
		nivel=preferencias.getInteger("nivel");
		random=new Random();
		//bitmap
		bitmap=new BitmapFont(Gdx.files.internal("letras/letrasjuegonuevo.fnt"));
		bitmap.setColor(Color.WHITE);
		//camara
		camara=new OrthographicCamera();
		camara.setToOrtho(false, 900, 540);
		
		//aciones con el dedo
		acciones=new Acciones();
		
		batch = new SpriteBatch();
		//texturas
		obstaculos = new Texture("texturas/obstaculo1.png");
		personajes = new Texture("texturas/personaje.png"); 
		barras=new Texture("texturas/barradeabilidad.png");
		play=new Texture("texturas/play.png");
		coins=new Texture("texturas/coins.png");
		barratras=new Texture("texturas/barradeconenido.png");
		tablero=new Texture("texturas/tablero.png");
		fondos=new Texture("texturas/fondos.png");
		logo=new Texture("texturas/logo.png");
		
		obstaculos.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		personajes.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		barras.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		play.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		coins.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		barratras.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		tablero.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		fondos.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		//texture regions
		personaje=TextureRegion.split(personajes, personajes.getWidth()/6, personajes.getHeight()/3);
		obstaculo=TextureRegion.split(obstaculos, obstaculos.getWidth()/2, obstaculos.getHeight()/3);
		coin=TextureRegion.split(coins, coins.getWidth(), coins.getHeight());
		fondo=TextureRegion.split(fondos, fondos.getWidth(), fondos.getHeight()/2);
		barra=TextureRegion.split(barras, barras.getWidth(), barras.getHeight()/2);

		//arrays
		posxobstaba=new int[cantidadobsta];
		posxobstari=new int[cantidadobsta];
		posxcoin=new int[canditadcoins];
		posycoin=new int[canditadcoins];
		
		//rectangles
		rectangleguia=new Rectangle(-40, -40, 50, 50);
		recpersonaje=new Rectangle(200, posyperso, 68, 100);
		rectangleplay=new Rectangle(350, 100, play.getWidth(), play.getHeight());
		//recobstamedio=new Rectangle(posxobstamedio, 203, 135, 135);
		
		rectangleobstaba=new Rectangle[cantidadobsta];
		rectangleobstari=new Rectangle[cantidadobsta];
		
		recsupba=new Rectangle[cantidadobsta];
		recsupri=new Rectangle[cantidadobsta];
		
		rectanglecoins=new Rectangle[canditadcoins];
		for(int v=0; v<canditadcoins; v++)
		{
			rectanglecoins[v]=new Rectangle(posxcoin[v], posycoin[v], 50, 50);
		}
		for(int cs=0; cs<cantidadobsta; cs++)
		{
			rectangleobstaba[cs]=new  Rectangle(posxobstaba[cs], 0, 135, 135);
			rectangleobstari[cs]=new  Rectangle(posxobstari[cs], 405, 135, 135);
			recsupba[cs]=new Rectangle(posxobstaba[cs], 134, 135, 4);
			recsupri[cs]=new Rectangle(posxobstari[cs], 406, 135, 4);
		}
		
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(255, 255, 255, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//bloquea el boton de regreso en el telefono (true bloquea, false desbloquea)
		if(pantalla==0 || pantalla==2)
		{
			Gdx.input.setCatchBackKey(true);
		}
		else if(pantalla==1)
		{
			Gdx.input.setCatchBackKey(false);
		}
		switch (pantalla) 
		{
		case 0:
			texturafija();
			batch.setProjectionMatrix(camara.combined);
			camara.update();
			if(Gdx.input.isKeyPressed(Keys.BACK))
			{
				musica[idmundo].stop();
				//anuncio
				//if(adsController.isWifiConnected()) {adsController.showBannerAd();}
				pantalla=1;
			}
			//camara.rotate(1);
			
			moverfondo();
			moverguia();
			if(perdio==0)
			{
				musica[idmundo].play();
				interacciones();
			}
			//System.out.println(nivel);
			perdio();
			batch.begin();
			//fondos
			fondos(0);
			//cargamos los personajes con su respectiva id
			personajes(idpersonaje);
			//cargamos los obstaculos con su respectiva id
			obstaculos(idmundo);
			//cargamos los coins
			coins(0);
			batch.draw(barratras, touchposbarra.x-2, touchposbarra.y-4, 24, 526);
			if(altobarra>270)
			{
				batch.draw(barra[0][0], touchposbarra.x, touchposbarra.y, 20, altobarra);
			}
			else if(altobarra<270)
			{
				batch.draw(barra[1][0], touchposbarra.x, touchposbarra.y, 20, altobarra);
			}
			if(perdio==1)
			{
				nivel=acciones.masnivel(puntos, nivel);
				preferencias.putInteger("nivel", nivel);
				preferencias.flush();
				if(tiempo>1.2f)
				{
					batch.draw(tablero, 30, 420);
				}
				
			}
			puntost=Integer.toString(puntos);
			bitmap.draw(batch, puntost, 40, 475);
			if(perdio==1)
			{
				musica[idmundo].stop();
				//anuncio
				//if(adsController.isWifiConnected()) {adsController.showBannerAd();}
				tiempo+=Gdx.graphics.getDeltaTime();
				if(tiempo>1.5f)
				{
					
					batch.draw(play, 350, 100);
					if(puntos>preferencias.getInteger("puntos"))
					{
						puntosguarda=puntos;
						preferencias.putInteger("puntos", puntos);
						preferencias.flush();
					}
					puntost=Integer.toString(puntosguarda);
					bitmap.draw(batch, puntost, 262, 475);
					if(Gdx.input.justTouched())
					{
						if(rectangleguia.overlaps(rectangleplay))
						{
							for(int c=0; c<cantidadobsta; c++)
							{
								posxobstaba[c]=(901+random.nextInt(800));
								posxobstari[c]=(901+random.nextInt(800));
							}
							for(int v=0; v<canditadcoins; v++)
							{
									posxcoin[v]=(1601+random.nextInt(1000));
									posycoin[v]=(135+random.nextInt(221));
									
							}
							bajar=1;
							posyperso=0;
							puntos=0;
							velocidad=10;
							altobarra=520;
							idmundo=random.nextInt(3);
							//anuncio
							//adsController.hideBannerAd();
							perdio=0;
						}
					}
				}
				
				
			}
			else if(perdio==0)
			{
				tiempo=0;
			}
			batch.end();
			
			break;

		case 1:
			batch.setProjectionMatrix(camara.combined);
			camara.update();
			
			moverguia();
			if(Gdx.input.justTouched())
			{
				if(rectangleguia.overlaps(rectangleplay))
				{
					for(int c=0; c<cantidadobsta; c++)
					{
						posxobstaba[c]=(901+random.nextInt(800));
						posxobstari[c]=(901+random.nextInt(800));
					}
					
					puntos=0;
					//anuncio
					//adsController.hideBannerAd();
					pantalla=0;
				}
			}
			
			batch.begin();
			//fondos
			fondos(0);
			batch.draw(logo, 160, 300);
			batch.draw(play, 350, 100);
			batch.end();
			break;
		case 2:
			if(Gdx.input.isKeyPressed(Keys.BACK))
			{
				musica[idmundo].stop();
				pantalla=1;
			}
			
			break;
		}
		
	}
	
	public void coins(int id)
	{
		
			for(int v=0; v<canditadcoins; v++)
			{
				if(posxcoin[v]<-60)
				{
					posxcoin[v]=(1601+random.nextInt(1000));
					posycoin[v]=(135+random.nextInt(221));
					
				}
				if(perdio==0)
				{
					posxcoin[v]-=velocidad;
				}
				
				batch.draw(coin[id][0], posxcoin[v], posycoin[v]);
				rectanglecoins[v].setPosition(posxcoin[v], posycoin[v]);
				if(recpersonaje.overlaps(rectanglecoins[v]) && perdio==0)
				{
					posxcoin[v]=(901+random.nextInt(1000));
					posycoin[v]=(135+random.nextInt(221));
					puntos++;
					velocidad=acciones.subirvelocidad(puntos, velocidad);
				}
			}
			
		
	}
	
	public void fondos(int id)
	{
		batch.draw(fondo[id][0], 0, 0);
	}
	
	public void moverfondo()
	{
		if(perdio==0)
		{
			for(int c=0; c<cantidadobsta; c++)
			{
				posxobstaba[c]-=velocidad;
				posxobstari[c]-=velocidad;
				if(posxobstaba[c]<-135)
				{
					posxobstaba[c]=(901+random.nextInt(800));
				}
				if(posxobstari[c]<-135)
				{
					posxobstari[c]=(901+random.nextInt(800));
				}
				rectangleobstaba[c].setPosition(posxobstaba[c], 0);
				rectangleobstari[c].setPosition(posxobstari[c], 405);
				recsupba[c].setPosition(posxobstaba[c], 135);
				recsupri[c].setPosition(posxobstari[c], 406);
			}
		}
		
		
		
	}
	
	public void personajes(int id)
	{
			//personaje
			if(bajar==1)
			{
				if(perdio==0)
				{
					indice=new TextureRegion[3];
					for(int cs=0; cs<3; cs++)
					{
						indice[cs]=personaje[id][cs];
					}
					
					animacion = new Animation(0.1f, indice);
					tiempo1+=Gdx.graphics.getDeltaTime();
					animacionlol= animacion.getKeyFrame(tiempo1, true);
					batch.draw(animacionlol, 200, posyperso);
				}
				else
				{
					batch.draw(personaje[id][1], 200, posyperso);
				}
				
				
			}
			else if(subir==1)
			{
				if(perdio==0)
				{
					indice=new TextureRegion[3];
					int d=0;
					for(int cs=3; cs<6; cs++)
					{
						indice[d]=personaje[id][cs];
						d+=1;
					}
					
					animacion = new Animation(0.1f, indice);
					tiempo1+=Gdx.graphics.getDeltaTime();
					animacionlol= animacion.getKeyFrame(tiempo1, true);
					batch.draw(animacionlol, 200, posyperso);
				}
				else
				{
					batch.draw(personaje[id][4], 200, posyperso);
				}
				
				
			}
			
		
		
	}
	
	public void texturafija()
	{
		touchposbarra.set(acciones.porcenposwitdth(99.5f), acciones.porcenposheight(0.92f), 0);
		camara.unproject(touchposbarra);
	}
	public void obstaculos(int id)
	{
			for(int cs=0; cs<cantidadobsta; cs++)
			{
				//obstaculos
				batch.draw(obstaculo[id][0], posxobstaba[cs], 0);
				
				
				batch.draw(obstaculo[id][1], posxobstari[cs], 405);
			}
	}
	
	public void moverguia()
	{
		if(Gdx.input.isTouched())
		{
			rectangleguia.setPosition(acciones.movercosasx(50f, camara), acciones.movercosasy(50f, camara));
		}
		
	}
	
	public void perdio()
	{
		if(altobarra==10)
		{
			perdio=1;
		}
		for(int x=0; x<cantidadobsta; x++)
		{
			if(recpersonaje.overlaps(rectangleobstaba[x])|| recpersonaje.overlaps(rectangleobstari[x]))
			{
				perdio=1;
			}
			
		}
		
		
	}
	
	public void interacciones()
	{
		
		if(posyperso!=0 || posyperso!=440)
		{
			if(altobarra>10 && perdio==0)
			{
				altobarra-=1;
			}
			if(altobarra<10)
			{
				altobarra=10;
			}
			
		}
		if(posyperso==0 || posyperso==440)
		{
			if(altobarra<520 && perdio==0)
			{
				altobarra+=6;
			}
			if(altobarra>520)
			{
				altobarra=520;
			}
			
		}
		
		if(Gdx.input.justTouched() && perdio==0)
		{
			
			if(subir==1 && posyperso==440 || subir==1 && posyperso<440 && posyperso>270)
			{
				bajar=1;
				subir=0;
			}
			
			if(bajar==1 && posyperso==0 || bajar==1 && posyperso>0 && posyperso<270)
			{
				subir=1;
				bajar=0;
			}
			
			recpersonaje.setPosition(200, posyperso);
			
		}
		
		if(posyperso>440)
		{
			posyperso=440;
			
		}
		if(posyperso<0)
		{
			posyperso=0;
			
		}
		
		if(subir==1 && posyperso<440)
		{
			if(posyperso>440 || posyperso==440)
			{
				posyperso=440;
				subir=0;
				bajar=1;
				
			}
			
			posyperso+=14;
			recpersonaje.setPosition(200, posyperso);
			
		}
		
		if(bajar==1 && posyperso>0)
		{
			if(posyperso<0 || posyperso==0)
			{
				posyperso=0;
				bajar=0;
				subir=1;
				
			}
			posyperso-=14;
			recpersonaje.setPosition(200, posyperso);
		}
		
		
		
		for(int xs=0; xs<cantidadobsta; xs++)
		{
			if(recpersonaje.overlaps(recsupba[xs]))
			{
				sonido.play();
				subir=1;
				bajar=0;
				posyperso=136;
				recpersonaje.setPosition(200, posyperso);
				
				
			}
			else if(posyperso<270 && posyperso==135 && bajar==1)
			{
				posyperso=0;
				recpersonaje.setPosition(200, posyperso);
			}
			if(recpersonaje.overlaps(recsupri[xs]))
			{
				sonido.play();
				bajar=1;
				subir=0;
				posyperso=304;
				recpersonaje.setPosition(200, posyperso);
				
			}
			else if(posyperso<270 && posyperso==405 && subir==1)
			{
				posyperso=440;
				recpersonaje.setPosition(200, posyperso);
			}
			if(recpersonaje.overlaps(rectangleobstaba[xs]))
			{
				
			}
			if(recpersonaje.overlaps(rectangleobstari[xs]))
			{
				
			}
		}
		
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
		personajes.dispose();
		barras.dispose();
		obstaculos.dispose();
		
		play.dispose();
		coins.dispose();
		barratras.dispose();
		tablero.dispose();
		fondos.dispose();
		logo.dispose();
	}
	
}
