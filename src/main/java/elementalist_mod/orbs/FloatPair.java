package elementalist_mod.orbs;

public class FloatPair {
	public float x;
	public float y;
	
	FloatPair(){
		x = 0f;
		y = 0f;
	}
	
	FloatPair(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public void Lerp(FloatPair target, float amount) {
		x = x*(1-amount) + target.x*amount;
		y = y*(1-amount) + target.y*amount;
	}
}
