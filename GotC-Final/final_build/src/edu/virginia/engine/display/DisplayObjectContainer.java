package edu.virginia.engine.display;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

public class DisplayObjectContainer extends DisplayObject {

	private ArrayList<DisplayObject> objects;
	
	public DisplayObjectContainer(String id) {
		super(id);
		setObjects(new ArrayList<DisplayObject>());
	}

	public DisplayObjectContainer(String id, String imageFileName) {
		super(id, imageFileName);
		setObjects(new ArrayList<DisplayObject>());
	}
	
	
	public boolean contains(DisplayObject obj){
		return objects.contains(obj);
	}
	
	public void add(DisplayObject obj){
		if(!contains(obj)){
			obj.setParent(this);
			objects.add(obj);
		}
	}
	
	public ArrayList<DisplayObject> getObjects() {
		return objects;
	}

	public void setObjects(ArrayList<DisplayObject> objects) {
		this.objects = objects;
	}
	
	@Override
	public void draw(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		super.draw(g2d);
		applyTransformations(g2d);
		if(getObjects().size() != 0){
			for(int i= 0; i < objects.size(); i++){
				objects.get(i).draw(g);
			}
		}
		reverseTransformations(g2d);
	}

	public void addChildAtIndex(int i, DisplayObjectContainer obj){
		objects.add(i, obj);
	}
	
	public void removeChild(DisplayObjectContainer obj){
		if(contains(obj)){
			objects.remove(obj);
		}
	}
	
	public void removeByIndex(int i){
		if(objects.size()>i){
			objects.remove(i);
		}
	}
	
	public void removeAll(){
		objects.clear();
	}
	
	public DisplayObject getChildByID(String s){
		for(int i = 0; i < objects.size(); i++){
			if(objects.get(i).getId() == s){
				return objects.get(i);
			}
		}
		return null;
	}
	
	public DisplayObject getChildByIndex(int i){
		if(objects.size()>i){
			return objects.get(i);
		}
		else{
			return null;
		}
	}
	
	@Override
	public void update(ArrayList<Integer> pressedKeys){
		super.update(pressedKeys);
		for(int i = 0; i<objects.size(); i++){
			objects.get(i).update(pressedKeys);
		}
	}
	
}
