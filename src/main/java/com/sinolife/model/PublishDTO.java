package com.sinolife.model;

public class PublishDTO{
	/**
	 * 版本下需求状态
	 * 1:未分配
	 * 2:审批中
	 * 3:开发中
	 * 4:内测中
	 * 5:已移交测试
	 * 6:已完成
	 */
	private Publish publish;
	//版本进度
	private Progress progress;
	private int all;
	private int unkown;
	private int approving;
	private int developing;
	private int in_testing;
	private int out_testing;
	private int finished;
	
	
	public Progress getProgress() {
		return progress;
	}
	public void setProgress(Progress progress) {
		this.progress = progress;
	}
	public Publish getPublish() {
		return publish;
	}
	public void setPublish(Publish publish) {
		this.publish = publish;
	}
	public int getAll() {
		return all;
	}
	public void setAll(int all) {
		this.all = all;
	}
	public int getUnkown() {
		return unkown;
	}
	public void setUnkown(int unkown) {
		this.unkown = unkown;
	}
	public int getApproving() {
		return approving;
	}
	public void setApproving(int approving) {
		this.approving = approving;
	}
	public int getDeveloping() {
		return developing;
	}
	public void setDeveloping(int developing) {
		this.developing = developing;
	}
	public int getIn_testing() {
		return in_testing;
	}
	public void setIn_testing(int in_testing) {
		this.in_testing = in_testing;
	}
	public int getOut_testing() {
		return out_testing;
	}
	public void setOut_testing(int out_testing) {
		this.out_testing = out_testing;
	}
	public int getFinished() {
		return finished;
	}
	public void setFinished(int finished) {
		this.finished = finished;
	}
}
