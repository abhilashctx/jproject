
public class ChessString {
	
	/*
		compress algorithm for strings
		Asked by benni in JavaScript
		Tags: combination, huffmann
		I have strings with a length of exactly 64 bytes. The strings are a combination of the following characters:
		'B','D','E','K','L','S','T','b','d','k','l','s','t'
		
		sample string:
		TELDKEETEBBEEBBEBESBESEBEELEBEEEsEEEbEEEEEElEsEEbbbbEbbbtEldEtkE
		
		There is a minimum of 32 'E' characters in the string. More are likely.
		There is also a max of 8 'B' and 'b', 2 of all other except 'K' and 'k' which exists only once.
		
		I'm looking for a algorithm which compress such strings to less than 37 bytes using only characters 'A'-'Z','a'-'z','0'-'9' on the compressed string. No other characters are allowed.
		
		A good explained algorithm would be fine. RLE, LHA or pure Huffmann are not an answer.
	
	*/
	
	/*
	 Yury_Delendik:
05/23/00 11:22 PM, ID: 2840330
Is it chess? ;) Good. Where is Queins?

I may squeezed less or equal 30 (<37!) chars from benni's string.

1) I form code such way:
  - delete all 'E' and store '1' if 'E' pressent and '0' else (= 64 bit)
  - for other chars if it in lower case I store '0' else '1' (<= 32 bit)
  - then I process 'B', 'S', 'T', 'D', 'L' (<= 32 + 18 + 14 + 10 + 6)
  - convert binary from '0' and '1' in number from '0-9','A-Z','a-z'

That is all.

My code:

function CharToBinPos(c, s)
{
  var t ="", b="";
  for(var j=0;j<s.length;j++)
    b+=s.charAt(j)==c?'1':(t+=s.charAt(j),'0');
  return new Array(b,t);
}

function InsertCharInBinPos(c, bin, s)
{
  var t ="", n=0, j=0;
  for(var i=0;i<s.length;i++)
  {
    if(s.charAt(i) == 'K')  
      t += bin.charAt(j++) == '0' ? (n++,'K') : c;
    else
      t += s.charAt(i);
  }
  return new Array(t, n, bin.length-n);
}

function BinTo62(b)
{
  var b2 = b, s ="";
  do {
    var j;
    var b3 = "";
    for(j=b2.length-1;j>=0 && b2.charAt(j) == '0';j--);
    var c = 0;
    for(;j>=0;j--)
    {
      c=2*c + (b2.charAt(j)=='0'?0:1);
      b3 = (c>=62?'1':'0') + b3; c=c%62;  
    }
    s += "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".charAt(c);
    b2 = b3;
  } while(b2 != "");
  return s;
}

function BinFrom62(s)
{
  var b = "";
  for(var i=s.length-1;i>=0;i--)
  {
    var d = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".search(s.charAt(i));
    var b2 = "";
    for(var j=0;j<b.length;j++)
    {
      d+=(b.charAt(j)=='0'?0:1)*62;
      b2 += (d & 1); d=Math.floor(d/2);  
    }
    while(d > 0)
    {
      b2 += (d & 1); d=Math.floor(d/2);  
    }
    b = b2;
  }
  while(b.length < 180) b+="0";
  return b;
}

function encode62(s)
{
  var a, b;
  a = CharToBinPos('E', s); b=a[0]; s=a[1];
  for(var j=0;j<s.length;j++)
    b+=s.charAt(j)>='a'?'0':'1';
  s=s.toUpperCase();
  a = CharToBinPos('B', s); b+=a[0]; s=a[1];
  a = CharToBinPos('S', s); b+=a[0]; s=a[1];
  a = CharToBinPos('T', s); b+=a[0]; s=a[1];
  a = CharToBinPos('D', s); b+=a[0]; s=a[1];
  a = CharToBinPos('L', s); b+=a[0]; s=a[1];
  return BinTo62(b);
}

function decode62(e)
{
  var b = BinFrom62(e);
  var s = "KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK";
  var a = InsertCharInBinPos('E', b.substr(0,64), s);
  var j = 64 + a[1], m;
  m = a[1]; a = InsertCharInBinPos('B', b.substr(j,m), a[0]); j+=m;
  m = a[1]; a = InsertCharInBinPos('S', b.substr(j,m), a[0]); j+=m;
  m = a[1]; a = InsertCharInBinPos('T', b.substr(j,m), a[0]); j+=m;
  m = a[1]; a = InsertCharInBinPos('D', b.substr(j,m), a[0]); j+=m;
  m = a[1]; a = InsertCharInBinPos('L', b.substr(j,m), a[0]); j+=m;
  s=""; j = 64;
  for(var i=0;i<64;i++)
  {
    if(a[0].charAt(i) == 'E') s+= 'E';
    else
      s+=(b.charAt(j++) == '0')?a[0].charAt(i).toLowerCase():a[0].charAt(i);
  }
  return s;
}

var s = "TELDKEETEBBEEBBEBESBESEBEELEBEEEsEEEbEEEEEElEsEEbbbbEbbbtEldEtkE";
var s0 = encode62(s);
var s1 = decode62(s0);

WScript.Echo(s0+", length: " + s0.length);
WScript.Echo(s+"\n"+ s1);

benni:
05/24/00 02:58 AM, ID: 2840768
yes it is chess - the queen is D (german "Dame") :-), I may post the url if you wish ...


Yury - I'm truly very impressed !!!

I needed a little time to understand what you are doing, but now it's (mostly ;-) ) clear. Do you create the algorithm by yourself?


ms99 - good idea (I had the same, but I wasn't able to implement a good "learning algorithm"), as you see Yury algorithm is much better, so I will give the points to him

mhervais
1.) I want to implement the algorithm in JavaScript not in Java (very confusing, I know ;-) )
2.) I believe with a good "learning algorithm" ms99's idea, can guarentee a string shorter than 37 bytes
3.) I have no clue, how your proposed algorithm encode the result string to the specified chars only
4.) I never said I need a fast algorithm :-)
5.) because of the specific layout of the string a "standard" compression algorithm won't give the best results


thanks to all of you - special thanks to Yury for is very exciting algorithm

benni
Yury_Delendik:
05/24/00 11:14 AM, ID: 2842205
Yes, I do it by my own. And I'm very modest. ;-) 
	 */
	
	public static void main(String[] args) {
		
	}
	
	public static void charToBin(int c, String s){
		
	}

}
