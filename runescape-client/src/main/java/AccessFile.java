import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.SyncFailedException;
import net.runelite.mapping.Export;
import net.runelite.mapping.Implements;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("oo")
@Implements("AccessFile")
public final class AccessFile {
	@ObfuscatedName("c")
	@Export("file")
	RandomAccessFile file;
	@ObfuscatedName("l")
	@ObfuscatedGetter(
		longValue = -3832987767694103853L
	)
	@Export("maxSize")
	final long maxSize;
	@ObfuscatedName("s")
	@ObfuscatedGetter(
		longValue = 897367112768993339L
	)
	@Export("offset")
	long offset;

	public AccessFile(File var1, String var2, long var3) throws IOException {
		if (-1L == var3) { // L: 11
			var3 = Long.MAX_VALUE;
		}

		if (var1.length() > var3) { // L: 12
			var1.delete(); // L: 13
		}

		this.file = new RandomAccessFile(var1, var2); // L: 15
		this.maxSize = var3; // L: 16
		this.offset = 0L; // L: 17
		int var5 = this.file.read(); // L: 18
		if (var5 != -1 && !var2.equals("r")) { // L: 19
			this.file.seek(0L); // L: 20
			this.file.write(var5); // L: 21
		}

		this.file.seek(0L); // L: 23
	} // L: 24

	@ObfuscatedName("c")
	@Export("seek")
	final void seek(long var1) throws IOException {
		this.file.seek(var1); // L: 27
		this.offset = var1; // L: 28
	} // L: 29

	@ObfuscatedName("l")
	@ObfuscatedSignature(
		descriptor = "([BIII)V",
		garbageValue = "-43751608"
	)
	@Export("write")
	public final void write(byte[] var1, int var2, int var3) throws IOException {
		if ((long)var3 + this.offset > this.maxSize) { // L: 32
			this.file.seek(this.maxSize); // L: 33
			this.file.write(1); // L: 34
			throw new EOFException(); // L: 35
		} else {
			this.file.write(var1, var2, var3); // L: 37
			this.offset += (long)var3; // L: 38
		}
	} // L: 39

	@ObfuscatedName("s")
	@ObfuscatedSignature(
		descriptor = "(I)V",
		garbageValue = "-1421008856"
	)
	@Export("close")
	public final void close() throws IOException {
		this.closeSync(false); // L: 42
	} // L: 43

	@ObfuscatedName("e")
	@ObfuscatedSignature(
		descriptor = "(ZI)V",
		garbageValue = "-2119255691"
	)
	@Export("closeSync")
	public final void closeSync(boolean var1) throws IOException {
		if (this.file != null) { // L: 46
			if (var1) { // L: 47
				try {
					this.file.getFD().sync(); // L: 49
				} catch (SyncFailedException var3) { // L: 51
				}
			}

			this.file.close(); // L: 53
			this.file = null; // L: 54
		}

	} // L: 56

	@ObfuscatedName("r")
	@ObfuscatedSignature(
		descriptor = "(B)J",
		garbageValue = "90"
	)
	@Export("length")
	public final long length() throws IOException {
		return this.file.length(); // L: 59
	}

	@ObfuscatedName("o")
	@ObfuscatedSignature(
		descriptor = "([BIII)I",
		garbageValue = "929552222"
	)
	@Export("read")
	public final int read(byte[] var1, int var2, int var3) throws IOException {
		int var4 = this.file.read(var1, var2, var3); // L: 63
		if (var4 > 0) {
			this.offset += (long)var4; // L: 64
		}

		return var4; // L: 65
	}

	protected void finalize() throws Throwable {
		if (this.file != null) { // L: 69
			System.out.println(""); // L: 70
			this.close(); // L: 71
		}

	} // L: 73
}
