package ZombieGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import edu.virginia.engine.display.AnimatedSprite;
import edu.virginia.engine.display.AnimatedZombie;
import edu.virginia.engine.display.Camera;
import edu.virginia.engine.display.DisplayObject;
import edu.virginia.engine.display.Game;
import edu.virginia.engine.display.PhysicsSprite;
import edu.virginia.engine.display.SoundManager;
import edu.virginia.engine.display.Sprite;
import edu.virginia.engine.tweening.TweenJuggler;
import edu.virginia.engine.util.GameClock;

public class ZombieGame extends Game {
	
	public boolean newGame = false;
	
	TweenJuggler tj = new TweenJuggler();
	
	/* ---------------- END SCREEN VARIABLES -------------------- */
	
	int zombies_killed = 0;
	int total_time_alive = 0;
	int zombie_waves_survived = 0;
	
	/* ---------------------------------------------------------- */
	
	int secrets_found = 0;

	/* ---------------- START SCREEN VARIABLES ------------------ */
	
	boolean secret_1_found = false;
	boolean show_start_screen = true;
	boolean show_settings_screen = false;
	boolean show_end_screen = false;
	boolean game_end = false;
	boolean game_started = false;
	boolean at_game_start = true;
	boolean onPlatform = false;
	boolean onPlatform2 = false;
	int select = 0;

	// start screen

	Sprite start_screen = new Sprite("Start Screen", "start_screen.png");
	Sprite start = new Sprite("Start", "start.png");
	Sprite high_scores = new Sprite("High Scores", "high_scores.png");
	Sprite settings = new Sprite("Settings", "settings.png");
	Sprite quit = new Sprite("Quit", "quit.png");
	Sprite start_screen_title = new Sprite("Start Screen Title", "game_title.png");
	Sprite selector = new Sprite("Selector", "bloody_hand.png");
	Sprite bomb_icon = new Sprite("bomb", "bomb.png");
	Sprite random_encounter = new Sprite("Random Encounter", "random.png");
	
	// settings screen

	Sprite difficulty = new Sprite("Difficulty", "select-difficulty.png");
	Sprite beginner = new Sprite("Beginner", "beginner.png");
	Sprite intermediate = new Sprite("Intermediate", "intermediate.png");
	Sprite expert = new Sprite("Expert", "expert.png");

	// end screen

	Sprite game_over = new Sprite("Game Over", "game_over.png");
	double game_end_time = 0;
	double final_score = 0;

	int added_zombies = 0;
	int text_frames = 300;

	/* ---------------------------------------------------------- */

	double gravity = 0.8;
	Camera cam = new Camera(0,0);
	boolean jumping = false;
	boolean jumpAllowed = true;
	boolean last_moving_left = false;
	boolean bombDropped = false;
	boolean explosion = false;
	public int numBombs = 5;
	PhysicsSprite floryan = new PhysicsSprite("floryan", "Floryan.png", 2, 6);
	DisplayObject zombieFloryan = new DisplayObject("zombieFloryan", "zombieFloryan.png");
	ArrayList<AnimatedZombie> zombies = new ArrayList<AnimatedZombie>();
	ArrayList<PhysicsSprite> bullets = new ArrayList<PhysicsSprite>();
	ArrayList<AnimatedZombie> students = new ArrayList<AnimatedZombie>();
	DisplayObject plusTen = new DisplayObject("plusTen", "plusTen.png");
	PhysicsSprite bomb = new PhysicsSprite("bomb", "bomb.png", 1, 1);
	AnimatedSprite explode = new AnimatedSprite("explosion", "explosion.png", 4, 3);

	PhysicsSprite randomEncounter = new PhysicsSprite("Random Encounter", "random-encounter.png", 4, 4);

	int visibleZombies = 0;
	int angle = 0;
	int zombieStudents = 0;
	int demon_health = 100;

	PhysicsSprite desk = new PhysicsSprite("Desk", "desk.png", 1,1);

	ArrayList<Sprite> chairs = new ArrayList<Sprite>();

	Sprite game_title = new Sprite("Guardian of the Classroom", "title.png");
	Sprite game_background = new Sprite("Background", "game_scene.png");
	Sprite chalkboard = new Sprite("Chalkboard", "chalkboard.png");
	Sprite clock = new Sprite("Clock", "clock.png");
	Sprite platform = new Sprite("Platform", "long-platform.png");
	Sprite platform2 = new Sprite("Platform", "long-platform.png");
	Sprite platform3 = new Sprite("Platform", "long-platform.png");
	Sprite platform4 = new Sprite("Platform", "long-platform.png");
	Sprite platform5 = new Sprite("Platform", "long-platform.png");


	GameClock total_time;
	GameClock count_down;
	GameClock sound_clock;

	private int lives = 3;
	private int score = 0;

	/* -------------------------------- */

	public ZombieGame() {
		super("Guardian of the Classroom", 1500, 1000);
		this.add(platform);
		this.add(platform2);
		this.add(platform3);
		this.add(platform4);
		this.add(floryan);
		
		this.add(randomEncounter);
		
		this.add(zombieFloryan);
		this.add(plusTen);
		zombieFloryan.setVisible(false);
		this.getMainFrame();
		
		explode.setAnimation("explode", 0, 11);
		bomb.setScaleX(0.3);
		bomb.setScaleY(0.3);
		explode.setVisible(false);
		
		bomb_icon.setScaleX(0.5f);
		bomb_icon.setScaleY(0.5f);

		floryan.setPositionY(1000-floryan.getUnscaledHeight()*2);
		floryan.setAnimation("move forward", 0, 3);
		floryan.setAnimation("move backward", 6, 9);
		floryan.setAnimation("jump", 4, 5);
		floryan.setAnimation("jump left", 10, 11);
		floryan.setAnimSpeed(70);
		
		platform.setPositionY(570);
		platform.setPositionX(2100);
		platform.setScaleX(0.75);
		platform.setScaleY(0.75);
		
		platform2.setPositionY(570);
		platform2.setPositionX(1650);
		platform2.setScaleX(0.75);
		platform2.setScaleY(0.75);
		
		platform3.setPositionY(370);
		platform3.setPositionX(950);
		platform3.setScaleX(0.75);
		platform3.setScaleY(0.75);
		
		platform4.setPositionY(370);
		platform4.setPositionX(400);
		platform4.setScaleX(0.75);
		platform4.setScaleY(0.75);
		
		plusTen.setVisible(false);
		plusTen.setScaleX(0.25);
		plusTen.setScaleY(0.25);

		Random r = new Random();
		
		randomEncounter.setAnimation("move backward", 4, 7);
		randomEncounter.setAnimation("move forward", 8, 11);
		randomEncounter.setCurrentFrame(8);
		randomEncounter.setPositionX(200);
		randomEncounter.setPositionY(715);
		randomEncounter.setScaleX(1.5);
		randomEncounter.setScaleY(1.5);
		randomEncounter.setVelocityX(5);
		randomEncounter.setVisible(false);

		// STUDENTS
		for (int i = 0; i < 5; i++) {
			students.add(new AnimatedZombie("student", "man.png", 3, 6));
			students.get(i).setImage(students.get(i).getDisplayImage());
			students.get(i).setX_position(r.nextInt(100)+300);
			students.get(i).setY_position(1010 - students.get(i).getUnscaledHeight()*2);
			students.get(i).setX_velocity(students.get(i).getRandomSpeed());
			students.get(i).setAnimSpeed(60);
			zombies.add(new AnimatedZombie("zombie", "zombies.png", 8,7));
			this.add(zombies.get(i));
			zombies.get(i).setX_position(students.get(i).getPositionX());
			zombies.get(i).setX_velocity(students.get(i).getX_velocity());
			zombies.get(i).setVisible(false);
			zombies.get(i).setAnimSpeed(40);
			this.add(students.get(i));

			if  (students.get(i).getX_velocity() > 0) {
				students.get(i).setAnimation("move forward", 0, 5);
				students.get(i).setCurrentFrame(0);
			}

			else {                                 
				students.get(i).setAnimation("move backward", 6, 11);
				students.get(i).setCurrentFrame(6);
			}
		}

		for (int i = 0; i < 5; i++) {
			zombies.add(new AnimatedZombie("zombie", "zombies.png", 8, 7));
			zombies.get(i).setImage(zombies.get(i).getDisplayImage());
			this.add(zombies.get(i+5));
			zombies.get(i+5).setX_position(r.nextInt(900)+2000); // random position between 50 and 749
			zombies.get(i+5).setY_position(1010 - zombies.get(i).getUnscaledHeight()*2);
			zombies.get(i+5).setX_velocity(zombies.get(i).getRandomSpeed());
			zombies.get(i+5).setAnimSpeed(40);

			if  (zombies.get(i+5).getX_velocity() > 0) {
				zombies.get(i+5).setAnimation("move forward", 7, 13);
				zombies.get(i+5).setCurrentFrame(7);
			}

			else {                                 
				zombies.get(i+5).setAnimation("move backward", 14, 20);
				zombies.get(i+5).setCurrentFrame(14);
			}
		}


		/* ------------------- CHAIRS ---------------------- */

		int nextChair = 700;
		for (int i = 0; i < 6; i++) {
			chairs.add(new Sprite("Chair", "chair.png"));
			chairs.get(i).setPositionX(nextChair);
			chairs.get(i).setPositionY(1000-chairs.get(i).getUnscaledHeight()*4);
			nextChair += 100;
		}

		//desk//
		this.add(desk);
		desk.setScaleX(0.2);
		desk.setScaleY(0.2);
		desk.setPositionX(600);
		desk.setPositionY(810);

		total_time = new GameClock();
		count_down = new GameClock();
		sound_clock = new GameClock();
		
		SoundManager.playStartScreenSound();
	}


	private boolean spacePressedLastFrame = false;
	private int framesPerBullet = 20;
	private int frameCounter = 0;
	private int framesPerLife = 60;
	private int frameLifeCounter = 0;
	private int framesForSounds = 0;
	private int framesForTen = 0;
	private int framesForBomb = 0;

	
	@Override
	public void update(ArrayList<Integer> pressedKeys) {
		visibleZombies=0;
		super.update(pressedKeys);
		
		if(plusTen.isVisible()){
			framesForTen++;
			if(framesForTen == 30){
				plusTen.setVisible(false);
				framesForTen = 0;
			}
		}
		
		if ((sound_clock != null) && (sound_clock.getElapsedTime() > 20000)) {
			SoundManager.playStartScreenSound();
			sound_clock.resetGameClock();
		}

		// SELECT OPTION FROM START MENU

		if (!show_settings_screen && (select == 200) && (pressedKeys.contains(KeyEvent.VK_SPACE))) {
			this.closeGame();
		}

		if (show_settings_screen) {
			for (int i = 0; i < pressedKeys.size(); i++) {
				if (pressedKeys.get(i) == KeyEvent.VK_SPACE) {
					pressedKeys.remove(i);
				}
			}
		}

		if (pressedKeys.contains(KeyEvent.VK_1) && !game_started) select = 0;
		if (pressedKeys.contains(KeyEvent.VK_2) && !game_started) select = 100;
		if (pressedKeys.contains(KeyEvent.VK_3) && !game_started) select = 200;

		if ((select == 100) && (pressedKeys.contains(KeyEvent.VK_SPACE))) {
			show_settings_screen = true;
		}
		
		if (show_settings_screen && (pressedKeys.contains(KeyEvent.VK_ENTER))) {

			show_settings_screen = false;

			if (select == 0)   added_zombies = 0;
			if (select == 100) added_zombies = 5;
			if (select == 200) added_zombies = 10;
		}

		// TAP SPACEBAR TO PLAY!
		if ((pressedKeys.contains(KeyEvent.VK_SPACE) || game_started) && (select == 0)) {
			show_start_screen = false;
			

			// add zombies
			if (at_game_start) {

				SoundManager.playZombieSpawn();
				Random r = new Random();

				// ZOMBIES
				for (int i = 10; i < added_zombies+10; i++) {
					//zombie_count++;
					zombies.add(new AnimatedZombie("zombie", "zombies.png", 8, 7));
					zombies.get(i).setImage(zombies.get(i).getDisplayImage());
					this.add(zombies.get(i));
					zombies.get(i).setX_position(r.nextInt(900)+2000); // random position between 50 and 749
					zombies.get(i).setY_position(1010 - zombies.get(i).getUnscaledHeight()*2);
					zombies.get(i).setX_velocity(zombies.get(i).getRandomSpeed());

					if  (zombies.get(i).getX_velocity() > 0) {
						zombies.get(i).setAnimation("move forward", 7, 13);
						zombies.get(i).setCurrentFrame(7);
					}

					else {                                 
						zombies.get(i).setAnimation("move backward", 14, 20);
						zombies.get(i).setCurrentFrame(14);
					}
				}

				at_game_start = false;

			}

			// Initialize game statistics
			if (pressedKeys.contains(KeyEvent.VK_SPACE) && !game_started) {
				total_time.resetGameClock();
				count_down.resetGameClock();
				score = 0;
				lives = 3; 

				game_started = true;
			}


			if(!spacePressedLastFrame && pressedKeys.contains(KeyEvent.VK_SPACE) && frameCounter == 0){
				shootBullet();
				SoundManager.playGunShotSound();
				frameCounter = 1;
			}
			if(frameCounter >= 1){
				frameCounter++;
				if(frameCounter == framesPerBullet) frameCounter = 0;
			}

			spacePressedLastFrame = pressedKeys.contains(KeyEvent.VK_SPACE);

			if(floryan.getPositionX()<cam.x+600 && cam.x>0){
				cam.x-=8;
			}
			if(floryan.getPositionX()>cam.x+800 && cam.x<1500){
				cam.x+=8;
			}
			if(frameLifeCounter >= 1){
				frameLifeCounter++;
				if(frameLifeCounter == framesPerLife) frameLifeCounter = 0;
			}
			
			// make floryan fall from platforms nicely
			if ((floryan.getPositionX() < 400 || floryan.getPositionX() > 1600) && onPlatform2) {
				onPlatform2 = false;
				jumping = true;
				jumpAllowed = false;
				floryan.setVelocityY(5.0);
			}
			if ((floryan.getPositionX() < 1650 || floryan.getPositionX() > 2700) && onPlatform) {
				onPlatform = false;
				jumping = true;
				jumpAllowed = false;
				floryan.setVelocityY(5.0);
			}
			
			// prevent floryan from falling through platform
			if (onPlatform) floryan.setPositionY(520); 
			if (onPlatform2) floryan.setPositionY(320); 

			
			//ZOMBIE FLORYAN CHANGE HERE, GAME OVER --> Eventually will be an animation
			
			for(int i = 0; i<zombies.size(); i ++){
				if(floryan.collidesWith(zombies.get(i)) && !zombies.get(i).getBlownUp() && frameLifeCounter == 0){
					if (lives > 0) lives--;
					SoundManager.playOw();
					frameLifeCounter ++;
					if (lives < 3) floryan.setAlpha(floryan.getAlpha()-0.25f);

					if(floryan.collidesWith(zombies.get(i)) && !zombies.get(i).getBlownUp() && (lives<1)){
						floryan.setVisible(false);
						zombieFloryan.setPositionX(floryan.getPositionX());
						zombieFloryan.setPositionY(floryan.getPositionY());
						zombieFloryan.setVisible(true);

						show_end_screen = true;

						game_end_time = total_time.getElapsedTime();
						final_score = score;
					}
				}
					
				if(zombies.get(i).isDead()){
					zombies.get(i).setVisible(false);
				}
			}

			if(floryan.getPositionY() > 1000-floryan.getUnscaledHeight()*2){
				floryan.setVelocityY(0);
				floryan.setAy(0);
				jumpAllowed = true;
				floryan.setPositionY(1000-floryan.getUnscaledHeight()*2);
				jumping = false;
			}
			if(pressedKeys.contains(KeyEvent.VK_RIGHT) && !jumping && floryan.getPositionX()<3000-floryan.getUnscaledWidth()){
				
				last_moving_left = false;
				//onPlatform = false;
				//onPlatform2 = false;
				
				floryan.setVelocityX(10);
				floryan.animate("move forward");
			}
			if(pressedKeys.contains(KeyEvent.VK_RIGHT) && jumping){
				
				//onPlatform = false;
				//onPlatform2 = false;
				
				last_moving_left = false;
				floryan.setAnimation("jump", 0, 5);
				floryan.animate("jump");
				floryan.setVelocityX(8);
			}
			if(pressedKeys.contains(KeyEvent.VK_LEFT) && jumping){
				
				//onPlatform = false;
				//onPlatform2 = false;
				
				last_moving_left = true;
				
				floryan.setAnimation("jump", 6, 11);
				floryan.animate("jump");
				
				floryan.setVelocityX(-8);
				floryan.animate("jump");
			}

			if(!pressedKeys.contains(KeyEvent.VK_RIGHT) && !pressedKeys.contains(KeyEvent.VK_LEFT)){
				floryan.setVelocityX(0.0);
			}

			if(pressedKeys.contains(KeyEvent.VK_DOWN)){
				floryan.animate("duck");
				if((onPlatform || onPlatform2) && framesForBomb == 0 && numBombs>0){
					this.add(bomb);
					bomb.setPositionX(floryan.getPositionX()+floryan.getUnscaledWidth()/3);
					bomb.setPositionY(floryan.getPositionY()+floryan.getUnscaledHeight());
					bomb.setVelocityY(5);
					framesForBomb++;
					bombDropped = true;
					numBombs --;
				}
			}
			
//BOMB STUFF
			if(zombie_waves_survived>0 && zombie_waves_survived%5 == 0){
				numBombs =5;
			}
			
			if(bombDropped){
				framesForBomb ++;
				if(framesForBomb > 300){
					framesForBomb = 0;
					explode.exploded = false;
					explode.setCurrentFrame(0);
					bombDropped = false;
				}
			}
			
			if(bomb.getPositionY() > 850){
				this.add(explode);
				explode.setPositionX(bomb.getPositionX()-explode.getUnscaledWidth()/2);
				explode.setPositionY(bomb.getPositionY());
				this.removeChild(bomb);
				bomb.setPositionY(600);
				bomb.setVelocityY(0.0);
				SoundManager.playBoom();
				explode.setVisible(true);
				explosion = true;
			}
			
			if(explosion && !explode.exploded){
				explode.animate("explode");
			}
			
			if(explode.exploded){
				explosion = false;
				this.removeChild(explode);
			}
			
			if(floryan.getVelocityY()!=0){
				floryan.setAy(gravity);
			}
			if(pressedKeys.contains(KeyEvent.VK_UP)&& jumpAllowed){
				
				onPlatform = false;
				onPlatform2 = false;
				
				if(floryan.getCurrentFrame()<6){
					floryan.setAnimation("jump", 0, 5);
				}
				else floryan.setAnimation("jump", 6, 11);
				
				floryan.animate("jump");
				jumping = true;
				jumpAllowed = false;
				floryan.setVelocityY(-20.0);
			}
			if(!pressedKeys.contains(KeyEvent.VK_DOWN) && !pressedKeys.contains(KeyEvent.VK_RIGHT)
					&& !jumping && !pressedKeys.contains(KeyEvent.VK_LEFT)){
				if (last_moving_left) {
					floryan.setCurrentFrame(6);
				}
				else {
					floryan.setCurrentFrame(0);
				}
				
			}
			if(pressedKeys.contains(KeyEvent.VK_LEFT) && !jumping && floryan.getPositionX()>0){
				
				last_moving_left = true;
				
				if(floryan.getPositionX()<0){
					floryan.setPositionX(0);
					floryan.setVelocityX(0);
				}
				else{
					floryan.setVelocityX(-10);
					floryan.animate("move backward");
				}
			}

			if (floryan.getPositionX() < 0) floryan.setPositionX(0);
			if (floryan.getPositionX() > 3000-floryan.getUnscaledWidth()) floryan.setPositionX(3000-floryan.getUnscaledWidth());
			
			if (desk.getPositionX() < 0) {
				desk.setInMotion(false);
				desk.setPositionX(0); 
				desk.setPositionY(810);
			}
			if (desk.getPositionX() > 2950) {
				desk.setInMotion(false);
				desk.setPositionX(2940);
				desk.setVelocityX(0);
				desk.setPositionY(810);
			}
			

			if (zombies != null) {
				for (AnimatedZombie zombie : zombies) {

					// HIT BY BOMB
					
					if(this.contains(explode) && zombie.getPositionX() > explode.getPositionX()-explode.getUnscaledWidth()/2 
							&& zombie.getPositionX() < explode.getPositionX()+explode.getUnscaledWidth()/2 && !zombie.getBlownUp()){
						zombies_killed++;
						score +=10;
						zombie.setCurrentFrame(43);
						zombie.setBlownUp(true);
					}
					
					// COLLISION WITH DESK
					if(zombie.isDead() || !zombie.isVisible()){
						visibleZombies --;
					}
					if (zombie.collidesWith(desk) && !desk.isInMotion() && (total_time.getElapsedTime() > 5000)&& !zombie.isDead()) {
						desk.setInMotion(true);
						desk.setPositionX(zombie.getPositionX());
						desk.setPositionY(zombie.getPositionY()+25);

						if (floryan.getPositionX() < zombie.getPositionX()) {
							desk.setVelocityX(-20);

							if (zombie.getX_velocity() > 0) {
								zombie.setX_velocity(-zombie.getX_velocity());

								if  (zombie.getX_velocity() > 0) {
									zombie.setAnimation("move forward", 7, 13);
									zombie.setCurrentFrame(7);
								}
								else {
									zombie.setAnimation("move backward", 14, 20);
									zombie.setCurrentFrame(14);
								}
							}
						}
						else {
							desk.setVelocityX(20);

							if (zombie.getX_velocity() < 0) {
								zombie.setX_velocity(-zombie.getX_velocity());

								if  (zombie.getX_velocity() > 0) {
									zombie.setAnimation("move forward", 7, 13);
									zombie.setCurrentFrame(7);
								}
								else {
									zombie.setAnimation("move backward", 14, 20);
									zombie.setCurrentFrame(14);
								}
							}
						}
					}

					if (floryan.collidesWith(desk) && desk.isInMotion()) {
						desk.setInMotion(false);
						desk.setVelocityX(0);
						desk.setPositionX(floryan.getPositionX());
						desk.setPositionY(810);
					}
					
					
					/* ------------------ RANDOM ENCOUNTER ------------------- */
					
					if (randomEncounter != null) {
						if(this.contains(explode) && randomEncounter.getPositionX() > explode.getPositionX()-explode.getUnscaledWidth()/2 
								&& randomEncounter.getPositionX() < explode.getPositionX()+explode.getUnscaledWidth()/2){
							demon_health-=10;
						}
						
						// collision w/ left wall
						if (randomEncounter.getPositionX() < 0) {
							randomEncounter.setCurrentFrame(8);
							randomEncounter.setVelocityX(-randomEncounter.getVelocityX());
						}

						// collision w/ right wall
						if (randomEncounter.getPositionX() > 3000) {
							randomEncounter.setCurrentFrame(4);
							randomEncounter.setVelocityX(-randomEncounter.getVelocityX());
						}

						// if moving forward, animate forward
						if (randomEncounter.getVelocityX() > 0) {
							randomEncounter.animate("move forward");
							randomEncounter.setAnimSpeed(100);
						}
						// if moving backward, animate backward
						else {
							randomEncounter.animate("move backward");
						}

						// follow Floryan
						if (floryan.getPositionX() < randomEncounter.getPositionX()) {
							randomEncounter.setVelocityX(-3);
						}
						else {
							randomEncounter.setVelocityX(3);
						}
						
						if(floryan.collidesWith(randomEncounter) && (randomEncounter.isVisible()) && (demon_health > 0) && frameLifeCounter == 0){
							if (lives > 0) lives--;
							SoundManager.playOw();
							frameLifeCounter ++;
							if (lives < 3) floryan.setAlpha(floryan.getAlpha()-0.25f);

							if(floryan.collidesWith(randomEncounter) && (randomEncounter.isVisible()) && (demon_health > 0) && (lives<1)){
								floryan.setVisible(false);
								zombieFloryan.setPositionX(floryan.getPositionX());
								zombieFloryan.setPositionY(floryan.getPositionY());
								zombieFloryan.setVisible(true);

								show_end_screen = true;

								game_end_time = total_time.getElapsedTime();
								final_score = score;
							}
						}	
						
						if (demon_health < 1) {
							randomEncounter.setCurrentFrame(0);
							randomEncounter.setVelocityX(0);
							
							if (randomEncounter.getAlpha() > 0) randomEncounter.setAlpha(randomEncounter.getAlpha()-0.001f);
							if (randomEncounter.getAlpha() <= 0) {
								randomEncounter.setAlpha(0);
//								score += 20;
								randomEncounter.setVisible(false);
							}
						}
						
					}
								
					/* ------------------------------------------------------ */
					
					
					// CHECK FOR COLLISION WITH LEFT WALL
					if (zombie.getPositionX() < 0) {
						zombie.setX_velocity(-zombie.getX_velocity());

						if  (zombie.getX_velocity() > 0) {
							zombie.setAnimation("move forward", 7, 13);
							zombie.setCurrentFrame(7);
						}
						else{
							zombie.setAnimation("move backward", 14, 20);
							zombie.setCurrentFrame(14);
						}
					}

					// CHECK FOR COLLISION WITH RIGHT WALL
					if (zombie.getPositionX() > 2950) {
						zombie.setX_velocity(-zombie.getX_velocity());

						if  (zombie.getX_velocity() > 0) {
							zombie.setAnimation("move forward", 7, 13);
							zombie.setCurrentFrame(7);
						}
						else{
							zombie.setAnimation("move backward", 14, 20);
							zombie.setCurrentFrame(14);
						}
					}
				}
			}

			if (zombies != null) {
				for (AnimatedZombie zombie : zombies) {
					if(!zombie.getBlownUp()){

						if (zombie.getX_velocity() > 0) {
							zombie.setAnimation("move forward", 7, 13);
							zombie.animate("move forward");
						}
						else {
							zombie.setAnimation("move backward", 14, 20);
							zombie.animate("move backward");
						}

						zombie.setX_position(zombie.getPositionX() + zombie.getX_velocity());
					}
					if(zombie.getBlownUp()){
						zombie.setX_velocity(0.0);
						zombie.setAnimation("blow up", 44, 55);
						zombie.animate("blow up");
						zombie.setAnimSpeed(100);
					}
				}
			}

			for(PhysicsSprite bullet : bullets){
				
				if (bullet.getPositionX() > 1000 && bullet.getPositionX() < 1030) {
					if (bullet.getPositionY() < 710 && bullet.getPositionY() > 670) {
						
						if (!secret_1_found) secrets_found++;
						
						bullet.setVisible(false);
						
						if (lives < 3) floryan.setAlpha((float)(floryan.getAlpha() + 0.25));
						if (lives < 5) lives++;
						secret_1_found = true;
					}
				}
				
				for(int i = 0; i<zombies.size(); i++){
					if(bullet.collidesWith(zombies.get(i)) && bullet.isVisible() && !zombies.get(i).getBlownUp()
							&&bullet.getPositionX() > cam.x && bullet.getPositionX()<cam.x+1500){
						bullet.setVisible(false);
						zombies_killed++;
						//System.out.println(String.format("Zombies Killed: %d", zombies_killed));
						zombies.get(i).setCurrentFrame(43);
						zombies.get(i).setBlownUp(true);
						plusTen.setPositionX(zombies.get(i).getPositionX());
						plusTen.setPositionY(zombies.get(i).getPositionY());
						plusTen.setVisible(true);
						score += 10;
					}
				}
				
				if (bullet.collidesWith(randomEncounter) && randomEncounter.isVisible() && bullet.isVisible()) {
					demon_health-=5;
					bullet.setVisible(false);
				}
			}
			
			// STUDENT COLLISIONS
			if (students != null) {

				for (int i = 0; i < students.size(); i++) {

					// CHECK FOR COLLISION WITH LEFT WALL
					if (students.get(i).getPositionX() < 0) {
						students.get(i).setX_velocity(-students.get(i).getX_velocity());
						students.get(i).setPositionX(students.get(i).getPositionX()+500);

						if  (students.get(i).getX_velocity() > 0) {
							students.get(i).setAnimation("move forward", 0, 5);
							students.get(i).setCurrentFrame(0);
						}
						else {
							students.get(i).setAnimation("move backward", 6, 11);
							students.get(i).setCurrentFrame(6);
						}
					}

					// CHECK FOR COLLISION WITH RIGHT WALL
					if (students.get(i).getPositionX() > 2950) {
						students.get(i).setX_velocity(-students.get(i).getX_velocity());

						if  (students.get(i).getX_velocity() > 0) {
							students.get(i).setAnimation("move forward", 0, 5);
							students.get(i).setCurrentFrame(0);
						}
						else {
							students.get(i).setAnimation("move backward", 6, 11);
							students.get(i).setCurrentFrame(6);
						}
					}

					for(AnimatedZombie zombie : zombies){
						if (students.get(i).collidesWith(zombie) && students.get(i).isVisible() && !zombie.isDead()){
							students.get(i).setVisible(false);
							zombies.get(i).setVisible(true);
							zombies.get(i).setY_position(students.get(i).getPositionY());
							zombies.get(i).setX_position(students.get(i).getPositionX());
							zombieStudents++;
						}
					}
				}	
			}
			
			if (students != null) {
				for (AnimatedZombie student : students) {
					
					if (student.getX_velocity() > 0) {
						student.setAnimation("move forward", 0, 5);
						student.animate("move forward");
					}
					else {

						student.setAnimation("move backward", 6, 11);
						student.animate("move backward");
					}

					student.setX_position(student.getPositionX() + student.getX_velocity());
				}
			}
			
			// Floryan Collision with Platform 1
			if ((floryan.getPositionX() > 1700) && (floryan.getPositionX() < 2700) && !onPlatform) {
				if ((floryan.getPositionY() < 540) && (floryan.getPositionY() > 520)) {
					floryan.setPositionY(520);
					floryan.setVelocityY(0);
					floryan.setAy(0);
					jumpAllowed = true;
					jumping = false;
					onPlatform = true;
				}
			}
			
			// platform 1
			if ((floryan.getPositionX() < 1650) || (floryan.getPositionX() > 2700)) {
				//onPlatform = false;
				//onPlatform2 = false;
				//floryan.setAy(gravity);
			}
			
			// platform 2
			
			// Floryan Collision with Platform 2
			if ((floryan.getPositionX() > 400) && (floryan.getPositionX() < 1600) && !onPlatform2) {
				if ((floryan.getPositionY() < 340) && (floryan.getPositionY() > 320)) {
					floryan.setPositionY(320);
					floryan.setVelocityY(0);
					floryan.setAy(0);
					jumpAllowed = true;
					jumping = false;
					onPlatform2 = true;
					
				}
			}
			
			// platform 2
			if ((floryan.getPositionX() < 1600) || (floryan.getPositionX() > 400)) {
				//onPlatform2 = false;
				//onPlatform = false;
				//floryan.setAy(gravity);
			}
			
			
			framesForSounds ++;
			if(framesForSounds > 500 && zombies.size()+visibleZombies>0){
				SoundManager.playZombieSound();
				framesForSounds = 0;
			}
			/* ---------- CHECK TIME/ UPDATE DIFFICULTY/ ADD ZOMBIES ------------*/

			if (count_down.getElapsedTime()/1000 > 30) {
				count_down.resetGameClock();
				SoundManager.playZombieSpawn();
				
				if ((lives > 0) && ((zombies.size()+visibleZombies) == 0)) {
					
					zombie_waves_survived++;
					
					if (zombie_waves_survived % 3 == 0) {
						randomEncounter.setAnimation("move backward", 4, 7);
						randomEncounter.setAnimation("move forward", 8, 11);
						randomEncounter.setCurrentFrame(8);
						randomEncounter.setPositionX(1500);
						randomEncounter.setPositionY(715);
						randomEncounter.setScaleX(1.5);
						randomEncounter.setScaleY(1.5);
						randomEncounter.setVelocityX(5);
						randomEncounter.setVisible(true);
						demon_health = 100;
						randomEncounter.setAlpha(1);
						
						text_frames = 0;
					}
					
					score += 100;
		
					Random r = new Random();
					zombies.clear();
					bullets.clear();
					
					System.gc();

					// ZOMBIES
					for (int i = 0; i < 5+added_zombies; i++) {
						
						int nextPosition = r.nextInt(2800);
						
						while ((nextPosition < floryan.getPositionX()+300) && (nextPosition > floryan.getPositionX()-300)) {
							nextPosition = r.nextInt(1400);
						}
						
						zombies.add(new AnimatedZombie("zombie", "zombies.png", 8, 7));
						zombies.get(i).setImage(zombies.get(i).getDisplayImage());
						this.add(zombies.get(i));
						zombies.get(i).setX_position(nextPosition); // random position between 50 and 749
						zombies.get(i).setY_position(1010 - zombies.get(i).getUnscaledHeight()*2);
						zombies.get(i).setX_velocity(zombies.get(i).getRandomSpeed());

						if  (zombies.get(i).getX_velocity() > 0) {
							zombies.get(i).setAnimation("move forward", 7, 13);
							zombies.get(i).setCurrentFrame(7);
						}

						else {                                 
							zombies.get(i).setAnimation("move backward", 14, 20);
							zombies.get(i).setCurrentFrame(14);
						}
					}
					
					added_zombies += 2;
				}

				increaseDifficulty();

			}
		}
	}

	private void shootBullet(){
		PhysicsSprite bullet = new PhysicsSprite("bullet", "bullet.png", 1, 1);
		bullet.setScaleX(0.03);
		bullet.setScaleY(0.03);
		this.add(bullet);
		bullets.add(bullet);
		bullet.setPositionY(floryan.getPositionY()+floryan.getUnscaledHeight()/2-10);
		if(floryan.getCurrentFrame()<6) {
			bullet.setVelocityX(25);
			bullet.setPositionX(floryan.getPositionX()+floryan.getUnscaledWidth());}
		if(floryan.getCurrentFrame()>=6){
			bullet.setVelocityX(-25);
			bullet.setPositionX(floryan.getPositionX());}
	}

	private void increaseDifficulty() {
		for (AnimatedZombie zombie : zombies) {
			if (zombie.getX_velocity() > 0) {
				zombie.setX_velocity(zombie.getX_velocity()+1);
			}
			else {
				zombie.setX_velocity(zombie.getX_velocity()-1);
			}
		}
	}

	@Override 
	public void draw(Graphics g){

		super.draw(g);

		if (show_start_screen) {

			if (show_settings_screen) drawSettingsScreen(g);
			else                      drawStartScreen(g);
		}
		else {

			if ((total_time != null && !show_end_screen)) {
				g.translate(-cam.x, 0);
				drawGameScene(g);
				super.draw(g);
				g.translate(-cam.x, 0);
			}
			else {

				if (((total_time.getElapsedTime()) < (game_end_time + 150)) && (!game_end)) {
					g.translate(-cam.x, 0);
					drawGameScene(g);
					super.draw(g);
					g.translate(-cam.x, 0);
				}
				else {
					game_end = true;
					drawEndScreen(g);
				}
			}
		}		
	}

	public static void main(String[] args) {
		ZombieGame game = new ZombieGame();
		game.start();
		
	}

	private void drawGameScene(Graphics g) {

		// DRAW BACKGROUND & GAME OBJECTS
		if (game_background != null)
			g.drawImage(game_background.getDisplayImage(), 0, 205,(int) (game_background.getUnscaledWidth()),(int) (game_background.getUnscaledHeight()), null);
		g.drawImage(game_background.getDisplayImage(), (int) (game_background.getUnscaledWidth()),205,
				(int) (game_background.getUnscaledWidth()), (int) (game_background.getUnscaledHeight()), null);
		if (chalkboard != null)
			g.drawImage(chalkboard.getDisplayImage(), 400, 675,(int) (chalkboard.getUnscaledWidth()),(int) (chalkboard.getUnscaledHeight()), null);
		if (clock != null)
			g.drawImage(clock.getDisplayImage(), 1000, 675,(int) (clock.getUnscaledWidth()),(int) (clock.getUnscaledHeight()), null);

		/* DRAW CHAIRS */
		if (chairs != null) {
			for (Sprite chair : chairs) {
				chair.draw(g);
			}
		}
		
		// DRAW DEMON HEALTH
		
		if (randomEncounter.isVisible()) {
			
			g.drawImage(random_encounter.getDisplayImage(), 400+cam.x, 200,(int) (random_encounter.getUnscaledWidth()),(int) (random_encounter.getUnscaledHeight()), null);
			
			g.setColor(Color.BLACK);
			g.drawRect((int)randomEncounter.getPositionX() + 50, (int)randomEncounter.getPositionY() - 50, 102, 15);
			
			if (demon_health > 66) g.setColor(Color.GREEN);
			if (demon_health <= 66 && demon_health > 33) g.setColor(Color.YELLOW);
			if (demon_health <= 33) g.setColor(Color.RED);
			
			g.fillRect((int)randomEncounter.getPositionX() + 51, (int)randomEncounter.getPositionY() - 49, demon_health, 15);
		}

		// DRAW GAME TITLE & INFORMATION BAR
		g.setColor(Color.DARK_GRAY);
		g.fillRect(cam.x, 0, 1500, 75);
		if (game_title != null)
			g.drawImage(game_title.getDisplayImage(), 300+cam.x, 10,(int) (game_title.getUnscaledWidth()),(int) (game_title.getUnscaledHeight()), null);
		g.setColor(Color.BLACK);
		g.fillRect(cam.x, 75, 1500, 130);

		// TIME REMAINING
		g.setColor(Color.RED);
		g.setFont(new Font("Consolas", Font.PLAIN, 25));
		g.drawString("Time Remaining", 40+cam.x, 115);
		g.setFont(new Font("Consolas", Font.PLAIN, 50));
		if (count_down != null)
			g.drawString(String.format("%d", (int)Math.floor(31-count_down.getElapsedTime()/1000)), 110+cam.x, 180);

		// REMAINING ZOMBIES
		g.setColor(Color.LIGHT_GRAY);
		g.setFont(new Font("Consolas", Font.PLAIN, 25));
		g.drawString("Remaining Zombies", 270+cam.x, 115);
		g.setFont(new Font("Consolas", Font.PLAIN, 50));
		g.drawString(String.format("%d", zombies.size()+visibleZombies), 355+cam.x, 180);
		//g.drawString(String.format("%d", zombie_count), 355+cam.x, 180);

		// REMAINING STUDENTS
		g.setFont(new Font("Consolas", Font.PLAIN, 25));
		g.drawString("Remaining Students", 540+cam.x, 115);
		g.setFont(new Font("Consolas", Font.PLAIN, 50));
		g.drawString(String.format("%d", students.size()-zombieStudents), 655+cam.x, 180);

		// REMAINING STUDENTS
		g.setFont(new Font("Consolas", Font.PLAIN, 25));
		g.drawString("Time Elapsed", 840+cam.x, 115);
		g.setFont(new Font("Consolas", Font.PLAIN, 50));
		if (total_time != null)
			g.drawString(String.format("%d", (int)total_time.getElapsedTime()/1000), 895+cam.x, 180);

		// REMAINING LIVES
		g.setColor(Color.GREEN);
		g.setFont(new Font("Consolas", Font.PLAIN, 25));
		g.drawString("Lives", 1085+cam.x, 115);
		g.setFont(new Font("Consolas", Font.PLAIN, 50));
		g.drawString(String.format("%d", lives), 1105+cam.x, 180);

		// REMAINING LIVES
		g.setColor(Color.YELLOW);
		g.setFont(new Font("Consolas", Font.PLAIN, 25));
		g.drawString("Total Score", 1235+cam.x, 115);
		g.setFont(new Font("Consolas", Font.PLAIN, 50));
		g.drawString(String.format("%d", score), 1305+cam.x, 180);
		
		// DRAW BOMB COUNT
		g.drawImage(bomb_icon.getDisplayImage(), 1360+cam.x, 235,(int) (bomb_icon.getUnscaledWidth()*0.8),(int) (bomb_icon.getUnscaledHeight()*0.8), null);
		g.setColor(Color.ORANGE);
		g.drawString(String.format("%d", numBombs), 1390+cam.x, 300);
		
	}

	public void drawStartScreen(Graphics g) {

		if (start_screen != null) {
			g.drawImage(start_screen.getDisplayImage(), 0, 0,(int) (start_screen.getUnscaledWidth()),(int) (start_screen.getUnscaledHeight()), null);
			g.drawImage(start_screen_title.getDisplayImage(), 95, 170, (int)(start_screen_title.getUnscaledWidth()), (int)(start_screen_title.getUnscaledHeight()), null);
			g.drawImage(start.getDisplayImage(), 630, 400, (int)(start.getUnscaledWidth()), (int)(start.getUnscaledHeight()), null);
			g.drawImage(settings.getDisplayImage(), 630, 500, (int)(settings.getUnscaledWidth()), (int)(settings.getUnscaledHeight()), null);
			g.drawImage(quit.getDisplayImage(), 630, 600, (int)(quit.getUnscaledWidth()), (int)(quit.getUnscaledHeight()), null);
			g.setFont(new Font("Times New Roman", Font.BOLD, 40));
			g.drawString("Press SPACE to Select Option", 500, 800);
			g.drawString("Use 1-2-3 to Move Hand", 550, 350);
			g.drawImage(selector.getDisplayImage(), 550, 420 + select, (int)(selector.getUnscaledWidth()), (int)(selector.getUnscaledHeight()), null);
		}
	}

	public void drawSettingsScreen(Graphics g) {

		if (start_screen != null) {
			g.drawImage(start_screen.getDisplayImage(), 0, 0,(int) (start_screen.getUnscaledWidth()),(int) (start_screen.getUnscaledHeight()), null);
			g.drawImage(difficulty.getDisplayImage(), 420, 170,(int) (difficulty.getUnscaledWidth()),(int) (difficulty.getUnscaledHeight()), null);
			g.drawImage(beginner.getDisplayImage(), 630, 400, (int)(beginner.getUnscaledWidth()), (int)(beginner.getUnscaledHeight()), null);
			g.drawImage(intermediate.getDisplayImage(), 630, 500, (int)(intermediate.getUnscaledWidth()), (int)(intermediate.getUnscaledHeight()), null);
			g.drawImage(expert.getDisplayImage(), 630, 600, (int)(expert.getUnscaledWidth()), (int)(expert.getUnscaledHeight()), null);
			g.setFont(new Font("Times New Roman", Font.BOLD, 40));
			g.drawString("Press ENTER to Select Option", 500, 800);
			g.drawString("Use 1-2-3 to Move Hand", 550, 350);
			g.drawImage(selector.getDisplayImage(), 550, 420 + select, (int)(selector.getUnscaledWidth()), (int)(selector.getUnscaledHeight()), null);
		}
	}

	public void drawEndScreen(Graphics g) {

		if (start_screen != null) {
			g.drawImage(start_screen.getDisplayImage(), 0, 0,(int) (start_screen.getUnscaledWidth()),(int) (start_screen.getUnscaledHeight()), null);
			g.drawImage(game_over.getDisplayImage(), 590, 100, (int)(game_over.getUnscaledWidth()), (int)(game_over.getUnscaledHeight()), null);
			g.setFont(new Font("Times New Roman", Font.BOLD, 50));
			g.setColor(Color.LIGHT_GRAY);
			g.drawString("Score:", 485, 400);
			g.setColor(Color.YELLOW);
			g.drawString(String.format("%d points", score), 810, 400);
			g.setColor(Color.LIGHT_GRAY);
			g.setFont(new Font("Times New Roman", Font.BOLD, 35));
			g.drawString("Time Alive:", 485, 550);
			g.drawString("Zombies Killed:", 485, 600);
			g.drawString("Waves Survived:", 485, 650);
			g.drawString("Secret Discovered:", 485, 700);
			g.drawString(String.format("%d seconds", (int)total_time.getElapsedTime()/1000), 810, 550);
			g.drawString(String.format("%d    (+%d points)", zombies_killed, 10*zombies_killed), 810, 600); 
			g.drawString(String.format("%d    (+%d points)", zombie_waves_survived, 100*zombie_waves_survived), 810, 650);
			g.drawString((secrets_found > 0) ? "True": "False", 810, 700);
			this.stop();
		}
	}
}