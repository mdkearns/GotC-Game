package edu.virginia.engine.display;

import java.io.File;
import java.util.Random;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundManager
{
	public static void playStartScreenSound() 
	{
		File sound = new File("./sounds/start-screen.wav");
		
		try 
		{
			Clip audioClip = AudioSystem.getClip();
			audioClip.open(AudioSystem.getAudioInputStream(sound));
			audioClip.start();
		}
		catch (Exception e) {}
	}
	
	public static void playGunShotSound() 
	{
		File sound = new File("./sounds/gun.wav");
		
		try 
		{
			Clip audioClip = AudioSystem.getClip();
			audioClip.open(AudioSystem.getAudioInputStream(sound));
			audioClip.start();
		}
		catch (Exception e) {}
	}
	
	public static void playZombieSpawn(){
		File sound = new File("./sounds/zombieSpawn.wav");
		try 
		{
			Clip audioClip = AudioSystem.getClip();
			audioClip.open(AudioSystem.getAudioInputStream(sound));
			audioClip.start();
		}
		catch (Exception e) {}
	}
	
	public static void playZombieSound(){
		Random r = new Random();
		int pick = r.nextInt(3);
		if(pick == 0){
			File sound = new File("./sounds/zombieSound.wav");
			try 
			{
				Clip audioClip = AudioSystem.getClip();
				audioClip.open(AudioSystem.getAudioInputStream(sound));
				audioClip.start();
			}
			catch (Exception e) {}
		}
		else if(pick == 1){
			File sound = new File("./sounds/comeHere.wav");
			try 
			{
				Clip audioClip = AudioSystem.getClip();
				audioClip.open(AudioSystem.getAudioInputStream(sound));
				audioClip.start();
			}
			catch (Exception e) {}
		}
		else if(pick == 2){
			File sound = new File("./sounds/gibberish.wav");
			try 
			{
				Clip audioClip = AudioSystem.getClip();
				audioClip.open(AudioSystem.getAudioInputStream(sound));
				audioClip.start();
			}
			catch (Exception e) {}
		}
	}
	
	public static void playOw(){
		File sound = new File("./sounds/pain.wav");
		try 
		{
			Clip audioClip = AudioSystem.getClip();
			audioClip.open(AudioSystem.getAudioInputStream(sound));
			audioClip.start();
		}
		catch (Exception e) {}
	}
	
	public static void playBoom(){
		File sound = new File("./sounds/boom.wav");
		try 
		{
			Clip audioClip = AudioSystem.getClip();
			audioClip.open(AudioSystem.getAudioInputStream(sound));
			audioClip.start();
		}
		catch (Exception e) {}
	}
}