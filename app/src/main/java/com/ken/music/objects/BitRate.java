package com.ken.music.objects;

import java.io.Serializable;

public class BitRate implements Serializable{
	boolean b128;
	boolean b320;
	boolean bLossless;

	public BitRate() {
		super();
	}

	public boolean isB128() {
		return b128;
	}

	public BitRate setB128(boolean b128) {
		this.b128 = b128;
		return this;
	}

	public boolean isB320() {
		return b320;
	}

	public BitRate setB320(boolean b320) {
		this.b320 = b320;
		return this;
	}

	public boolean isbLossless() {
		return bLossless;
	}

	public BitRate setbLossless(boolean bLossless) {
		this.bLossless = bLossless;
		return this;
	}

}
