package com.ken.music.objects;

import java.io.Serializable;

public class LinkDownload implements Serializable{
	String l128;
	String l320;
	String lLossLess;

	public LinkDownload() {
		super();
	}

	public String getS128() {
		return l128;
	}

	public LinkDownload setS128(String s128) {
		this.l128 = s128;
		return this;
	}

	public String getS320() {
		return l320;
	}

	public LinkDownload setS320(String s320) {
		this.l320 = s320;
		return this;
	}

	public String getsLossLess() {
		return lLossLess;
	}

	public LinkDownload setsLossLess(String sLossLess) {
		this.lLossLess = sLossLess;
		return this;
	}

}
