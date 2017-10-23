package com.juyo.visa.common.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

public class ValidateImageUtil {
	private ByteArrayInputStream image;
	private String str;

	private ValidateImageUtil() {
		init();
	}

	public static ValidateImageUtil Instance() {
		return new ValidateImageUtil();
	}

	public ByteArrayInputStream getImage() {
		return this.image;
	}

	public String getString() {
		return this.str;
	}

	private void init() {
		int width = 135;
		int height = 25;
		BufferedImage image = new BufferedImage(width, height, 1);

		Graphics g = image.getGraphics();

		Random random = new Random();

		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);

		g.setFont(new Font("宋体", 1, 20 + random.nextInt(6)));

		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 155; ++i) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}

		Map<String, Integer> numMap = new HashMap<String, Integer>();
		numMap.put("0", Integer.valueOf(0));
		numMap.put("1", Integer.valueOf(1));
		numMap.put("2", Integer.valueOf(2));
		numMap.put("3", Integer.valueOf(3));
		numMap.put("4", Integer.valueOf(4));
		numMap.put("5", Integer.valueOf(5));
		numMap.put("6", Integer.valueOf(6));
		numMap.put("7", Integer.valueOf(7));
		numMap.put("8", Integer.valueOf(8));
		numMap.put("9", Integer.valueOf(9));
		numMap.put("10", Integer.valueOf(10));

		String[] footerNum = { "0", "1", "2", "3", "4", "5" };
		String[] topNum = { "5", "6", "7", "8", "9", "10" };
		String[] operation = { "+", "-" };

		String num1 = String.valueOf(topNum[random.nextInt(topNum.length)]);

		String num2 = String.valueOf(footerNum[random.nextInt(footerNum.length)]);
		String opt = String.valueOf(operation[random.nextInt(operation.length)]);
		int numOne = ((Integer) numMap.get(num1)).intValue();
		int numTwo = ((Integer) numMap.get(num2)).intValue();
		if ("+".equals(opt))
			this.str = String.valueOf(numOne + numTwo);
		else {
			this.str = String.valueOf(numOne - numTwo);
		}

		g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));

		g.drawString(num1, 6, 16);

		g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));

		g.drawString(opt, 32, 16);

		g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));

		g.drawString(num2, 58, 16);

		g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));

		g.drawString("=", 84, 16);

		g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));

		g.drawString("?", 110, 16);

		g.dispose();
		ByteArrayInputStream input = null;
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		try {
			ImageOutputStream imageOut = ImageIO.createImageOutputStream(output);
			ImageIO.write(image, "JPEG", imageOut);
			imageOut.close();
			input = new ByteArrayInputStream(output.toByteArray());
		} catch (Exception e) {
			System.out.println("验证码图片产生出现错误：" + e.toString());
		}

		this.image = input;
	}

	private Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
}
