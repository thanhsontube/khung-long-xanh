package com.example.sonnt_commonandroid.constants;

import java.util.ArrayList;
import java.util.List;

public class LibDataSource {
	public static  String[] books = new String[] {
        "A Tale of Two Cities", "Le Petit Prince", "Dream of the Red Chamber", 
        "How to Win Friends and Influence People", "The Magic", 
        "And Then There Were None", "Think and Grow Rich", "She", "O Alquimista", 
        "Harry Potter and the Deathly Hallows", "Steps to Christ", "The Ginger Man",
        "Lolita", "Charlotte's Web", "Heidi's Years of Wandering and Learning", 
        "Anne of Green Gables", "Black Beauty", "Il Nome della Rosa", 
        "Watership Down", "The Secret", "Jonathan Livingston Seagull",
        "A Message to Garcia", "Sophie's World", "Angels and Demons",
        "How the Steel Was Tempered", "War and Peace", "You Can Heal Your Life",
        "Kane and Abel", "The Diary of Anne Frank", "To Kill a Mockingbird",
        "Valley of the Dolls", "Gone with the Wind", "One Hundred Years of Solitude",
        "Who Moved My Cheese?", "The Wind in the Willows", "Nineteen Eighty-Four",
        "Love Story", "Wolf Totem", "Jaws", "Love You Forever", 
        "What to Expect When You're Expecting", "Kon-Tiki: Across the Pacific in a Raft",
        "Where the Wild Things Are", "Fear of Flying", "Goodnight Moon", 
        "Guess How Much I Love You", "Perfume", "God's Little Acre", "Dune",
        "No Longer Human", "Catch-22", "Eye of the Needle", "Wild Swans"
    };
	
	public static List<String> listBooks (){
		List<String> list = new ArrayList<String>();
		for (String s : books) {
			list.add(s);
		}
		return list;
	}
}
