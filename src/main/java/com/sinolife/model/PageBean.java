package com.sinolife.model;

import java.util.List;
/**
 * 分页Bean
 * @author Flystar
 *
 */
@SuppressWarnings("rawtypes")
public class PageBean {

	private List list;//每页的数据
	private int totalrecord;//总共记录数
	private int pagesize;//每页显示的记录数
	private int totalpage;//总页数
	private int currentpage;//当前页数
	private int previouspage;//前一页
	private int nextpage;//下一页
	private int[] pagebar;//页码条
	
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	public int getTotalrecord() {
		return totalrecord;
	}
	public void setTotalrecord(int totalrecord) {
		this.totalrecord = totalrecord;
	}
	public int getPagesize() {
		return pagesize;
	}
	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}
	public int getTotalpage() {
		//100   5   20
		//101   5   21  =20---1
		//99    5   20  =19---4
		
		if(this.totalrecord%this.pagesize==0){
			this.totalpage = this.totalrecord/this.pagesize;
		}else{
			this.totalpage = this.totalrecord/this.pagesize+1;
		}
		return totalpage;
	}
	
	public int getCurrentpage() {
		return currentpage;
	}
	public void setCurrentpage(int currentpage) {
		this.currentpage = currentpage;
	}
	public int getPreviouspage() {
		if(this.currentpage-1<1){
			this.previouspage = 1;
		}else{
			this.previouspage = this.currentpage-1;
		}
		return previouspage;
	}
	
	public int getNextpage() {
		if(this.currentpage+1>=this.totalpage){
			this.nextpage = this.totalpage;
		}else{
			this.nextpage = this.currentpage +1;
		}
		return nextpage;
	}
	
	public int[] getPagebar() {
		int startpage;
		int endpage;
		int pagebar[] = null;
		//默认情况显示页数10
		if(this.totalpage<=10){
			pagebar = new int[this.totalpage];
			startpage = 1;
			endpage = this.totalpage;
		}else{
			//当页数大于10,此时根据当前页数来获取页码数
			pagebar = new int[10];
			startpage = this.currentpage - 4;
			endpage = this.currentpage + 5;
			if(startpage<1){
				startpage = 1;
				endpage = 10;
			}
			
			if(endpage>this.totalpage){
				endpage = this.totalpage;
				startpage = this.totalpage - 9;
			}
		}
		
		int index = 0;
		for(int i=startpage;i<=endpage;i++){
			pagebar[index++] = i;
		}
		
		this.pagebar = pagebar;
		return this.pagebar;
		/*int pagebar[] = new int[this.totalpage];
		for(int i=1;i<=this.totalpage;i++){
			pagebar[i-1] = i;
		}
		this.pagebar = pagebar;
		return pagebar;*/
	}
	
}
