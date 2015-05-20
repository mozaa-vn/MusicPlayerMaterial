package com.ken.music.objects;

import java.io.Serializable;

public class SourcePlay implements Serializable {
	String s128;
	String s320;
	String Lossless;

	public SourcePlay() {
		super();
	}

	public String getS128() {
		return s128;
	}

	public SourcePlay setS128(String s128) {
		this.s128 = s128;
		return this;
	}

	public String getS320() {
		return s320;
	}

	public SourcePlay setS320(String s320) {
		this.s320 = s320;
		return this;
	}

	public String getLossless() {
		return Lossless;
	}

	public SourcePlay setLossless(String lossless) {
		Lossless = lossless;
		return this;
	}

}
