package udt.packets;


public class PacketUtil {

	public static byte[]encode(long value){
		byte m4= (byte) (value>>24 );
		byte m3=(byte)(value>>16);
		byte m2=(byte)(value>>8);
		byte m1=(byte)(value);
		return new byte[]{m4,m3,m2,m1};
	}
	
	public static byte[]encodeSetHighest(boolean highest,long value){
		byte m4;
		if(highest){
			m4= (byte) (0x80 | value>>24 );
		}
		else{
			m4= (byte) (0x7f & value>>24 );
		}
		byte m3=(byte)(value>>16);
		byte m2=(byte)(value>>8);
		byte m1=(byte)(value);
		return new byte[]{m4,m3,m2,m1};
	}
	
	
	
	public static byte[]encodeSetHighestAndType(boolean highest,int type,long value){
		byte m4;
		byte m3;
		if(highest){
			m4= (byte) (0x80 | type<<3);
			m3= (byte) (0);
		}
		else{
			m4= (byte) (0x7f & value>>24 );
		    m3=(byte)(value>>16);
		}
		
		byte m2=(byte)(value>>8);
		byte m1=(byte)(value);
		return new byte[]{m4,m3,m2,m1};
	}
	
	public static byte[]encodeHighesBitTypeAndSeqNumber(boolean highestBit,int type, long value){
		byte m4,m3;
		if(highestBit){
		 m4=(byte) (0x80 | type<<3);
		 m3=(byte)(0);
		}
		else{
			m4= (byte) (0);
		    m3=(byte)(0);	
		}
		byte m2=(byte)(value>>8);
		byte m1=(byte)(value);
		return new byte[]{m4,m3,m2,m1};
	}
	
	
	public static long decode(byte[]data, int start){
		long result = (data[start] & 0xFF)<<24
		             |(data[start+1] & 0xFF)<<16
					 |(data[start+2] & 0xFF)<<8
					 |(data[start+3] & 0xFF);
		return result;
	}
	
	
	public static int decodeType(byte[]data, int start){
		int result =  (data[start]&0x78)>>3;
		return result;
	}
	
	public static long decodeAckSeqNr(byte[]data, int start){
		long result = (data[start+2] & 0xFF)<<8
					 |(data[start+3] & 0xFF);
		return result;
	}
	
	
	/**
	 * Gibt einen int-Wert zurueck, der das Byte im Bereich von 0 bis 
	 * 255 repraesentiert (negative Bytes werden umgewandelt)
	 *@param b zu konvertierendes Byte
	 *@return positiver Byte-Wert
	 */
	public static int castPositive(byte b)
	{
		return b < 0 ? b + 256 : b;
	}
	
	/**
	 * Die Methode erstellt aus dem gegebenen byte-array eine Dezimalzahl vom
	 * Typ long, indem jedes p als Zahl zur Basis 256 interpretiert wird
	 * (gelesen: stelle 0 = b[b.length-1] ...)
	 *@param b umzuwandelndes Byte-Array
	 *@return entsprechende long-Zahl
	 */
	public static long toDecValue(byte[] b) {
		if(b != null)
		{
			long result = 0;
			for (int i = 0; i < b.length; i++) {
				long l = castPositive(b[i]);
				result += l * Math.pow(256, b.length - i - 1);
			}
			return result;
		}
		return 0;
	}
	
//	private Hilfsmethoden
	/*Hilfsmethode zur Darstellung des 2-stelligen hexadezimalen Werts von b als String.
	 *Negative byte-Werte werden ueber einen int-cast (und "Betrag+127") in positive Zahlen konvertiert. 
	 *(Anomalie: grosse byte-Werte werden im IEEE-2er-Komplement dargestellt?)
	 */
	public static String toHexString(byte b)
	{
		String hex = Integer.toHexString(castPositive(b)).toUpperCase();
		if(hex.length() == 1) hex = "0" + hex;
		return hex;
	}
	
	public static long convertByteArrayToLong(byte[] buffer) {
		if (buffer.length != 4) {
 			throw new IllegalArgumentException("buffer length must be 4 bytes!");
   		}
   		long 
 		value  = buffer[0] << 24;
 		value |= buffer[1] << 16;
 		value |= buffer[2] << 8;
   		value |= buffer[3];
   		return value;
   	}
   
   	public static byte[] convertIntToByteArray(int val) {
   		byte[] buffer = new byte[4];
   
   		buffer[0] = (byte) (val >> 24);
   		buffer[1] = (byte) (val >> 16);
   		buffer[2] = (byte) (val >> 8);
   		buffer[3] = (byte) val;
   		
   		return buffer;
   	}
   	   		
}