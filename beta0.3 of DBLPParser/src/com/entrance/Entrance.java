package com.entrance;

import com.main.Parser;
//³ÌĞòÈë¿Ú
public class Entrance {
	 public static void main(String[] args) {
	      if (args.length < 1) {
	         System.out.println("Usage: java Parser [input]");
	         System.exit(0);
	      }
	      System.out.println("begin");
	      Parser p = new Parser(args[0]);
	      
	   }
}
