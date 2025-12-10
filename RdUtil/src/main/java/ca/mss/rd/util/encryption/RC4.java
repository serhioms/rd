package ca.mss.rd.util.encryption;

public class RC4 {
	
    private final byte[] F = new byte[256];
    private final byte[] T = new byte[256];
    private final int keylen;

    public RC4(final byte[] key) {
        keylen = key.length;

        if (keylen < 1 || keylen > 256)
            throw new IllegalArgumentException("key must be between 1 and 256 bytes");
        
        int d,b=0;
        for(d=0; d < 256; d++) {
            F[d] = (byte) d;
            T[d] = key[d % keylen];
        }
        for(d=0; d < 256; d++) {
            b = (b + F[d] + T[d]) & 0xFF;
            F[d] ^= F[b];
            F[b] ^= F[d];
            F[d] ^= F[b];
        }
    }
    
    public byte[] decrypt(final byte[] g) {
        final byte[] c = new byte[g.length];
        int d=0, b=0;
        for(int h=0; h<g.length; h++) {
            d = (d + 1) & 0xFF;
            b = (b + F[d]) & 0xFF;
            
            F[d] ^= F[b];
            F[b] ^= F[d];
            F[d] ^= F[b];

            c[h] = (byte) (g[h] ^ F[(F[d] + F[b]) & 0xFF]);
        }
        return c;
    }

}