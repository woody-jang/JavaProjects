import java.time.LocalTime;

public class TextAreaData {
	private int id;
	private LocalTime saveTime;
	private String saveData;
	private boolean isAuto;

	public TextAreaData(int id, LocalTime saveTime, boolean isAuto) {
		this.id = id;
		this.saveTime = saveTime;
		this.isAuto = isAuto;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalTime getSaveTime() {
		return saveTime;
	}

	public void setSaveTime(LocalTime saveTime) {
		this.saveTime = saveTime;
	}

	public String getSaveData() {
		return saveData;
	}

	public void setSaveData(String saveData) {
		this.saveData = saveData;
	}

	public boolean isAuto() {
		return isAuto;
	}

	public void setAuto(boolean isAuto) {
		this.isAuto = isAuto;
	}
}
