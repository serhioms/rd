package ca.mss.rd.util.encryption;

public class RC4Oanda {
	
    private final byte[] FF = new byte[256];
    private final byte[] TT = new byte[256];
    
    private final int keylen;

    public RC4Oanda(final byte[] key) {
        keylen = key.length;

        if (keylen < 1 || keylen > 256)
            throw new IllegalArgumentException("key must be between 1 and 256 bytes");
        
        int d, b=0;
        byte a;

        for(d=0; d < 256; d++) {
            FF[d] = (byte) d;
            TT[d] = key[d % keylen];
        }
        
        for(d=0; d < 256; d++) {
            b = (b + FF[d] + TT[d]) & 0xFF;
            
            a = FF[d];
            FF[d] = FF[b];
            FF[b] = a;
        }
    }
    
    public byte[] decrypt(final byte[] g) {
        final byte[] c = new byte[g.length];
        final byte[] F = FF.clone(), T = TT.clone();

        int d=0, b=0;
        byte a;
        
        for(int h=0; h<g.length; h++) {
            d = (d + 1) & 0xFF;
            b = (b + F[d]) & 0xFF;
            
            a = F[d];
            F[d] = F[b];
            F[b] = a;
            
            c[h] = (byte) (g[h] ^ F[(F[d] + F[b]) & 0xFF]);
        }
        return c;
    }

}