/* Transformed JS from: /jslib/rc4.js */
var key = "aaf6cb4f0ced8a211c2728328597268509ade33040233a11af";
function hexEncode(e) {
	var d = "0123456789abcdef", b = [], a = [], c;
	for (c = 0; c < 256; c++) {
		b[c] = d.charAt(c >> 4) + d.charAt(c & 15)
	}
	for (c = 0; c < e.length; c++) {
		a[c] = b[e.charCodeAt(c)]
	}
	return a.join("")
}
function hexDecode(f) {
	var e = "0123456789abcdef", b = [], a = [], c = 0, d;
	for (d = 0; d < 256; d++) {
		b[e.charAt(d >> 4) + e.charAt(d & 15)] = String.fromCharCode(d)
	}
	if (!f.match(/^[a-f0-9]*$/i)) {
		return false
	}
	if (f.length % 2) {
		f = "0" + f
	}
	for (d = 0; d < f.length; d += 2) {
		a[c++] = b[f.substr(d, 2)]
	}
	return a.join("")
}
function rc4(e, g) {
	var b = 0, d, a, h, f = [], c = [];
	for (d = 0; d < 256; d++) {
		f[d] = d
	}
	for (d = 0; d < 256; d++) {
		b = (b + f[d] + e.charCodeAt(d % e.length)) % 256;
		a = f[d];
		f[d] = f[b];
		f[b] = a
	}
	d = 0;
	b = 0;
	for (h = 0; h < g.length; h++) {
		d = (d + 1) % 256;
		b = (b + f[d]) % 256;
		a = f[d];
		f[d] = f[b];
		f[b] = a;
		c[c.length] = String.fromCharCode(g.charCodeAt(h)
				^ f[(f[d] + f[b]) % 256])
	}
	return c.join("")
}
function rc4decrypt(a) {
	return rc4(key, hexDecode(a))
};