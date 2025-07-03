using BCrypt.Net;
using System.Diagnostics;
using System.Security.Cryptography;
using System.Text;

namespace PharmacyApi.Utils
{
	public class PasswordHasher
	{
		private static string PasswordKey;

		public static void SetPasswordKey(string passwordKey) =>
			PasswordKey = passwordKey;

		public static string Encrypt(string password)
		{

			using (Aes aesAlg = Aes.Create())
			{
				aesAlg.Key = Encoding.UTF8.GetBytes(PasswordKey);
				aesAlg.Mode = CipherMode.ECB;
				aesAlg.Padding = PaddingMode.PKCS7;

				ICryptoTransform encryptor = aesAlg.CreateEncryptor(aesAlg.Key, aesAlg.IV);
				byte[] encryptedPassword;

				using (var msEncrypt = new System.IO.MemoryStream())
				{
					using (var csEncrypt = new CryptoStream(msEncrypt, encryptor, CryptoStreamMode.Write))
					{
						using (var swEncrypt = new System.IO.StreamWriter(csEncrypt))
						{
							swEncrypt.Write(password);
						}
						encryptedPassword = msEncrypt.ToArray();
					}
				}
				return Convert.ToBase64String(encryptedPassword);
			}
		}

		public static string Decrypt(string encryptedPassword)
		{
			using (Aes aesAlg = Aes.Create())
			{
				aesAlg.Key = Encoding.UTF8.GetBytes(PasswordKey);
				aesAlg.Mode = CipherMode.ECB;
				aesAlg.Padding = PaddingMode.PKCS7;

				ICryptoTransform decryptor = aesAlg.CreateDecryptor(aesAlg.Key, aesAlg.IV);
				byte[] encryptedBytes = Convert.FromBase64String(encryptedPassword);
				string decryptedPassword = null;

				using (var msDecrypt = new System.IO.MemoryStream(encryptedBytes))
				{
					using (var csDecrypt = new CryptoStream(msDecrypt, decryptor, CryptoStreamMode.Read))
					{
						using (var srDecrypt = new System.IO.StreamReader(csDecrypt))
						{
							decryptedPassword = srDecrypt.ReadToEnd();
						}
					}
				}
				return decryptedPassword;
			}
		}

		public static string Decrypt2(string password)
		{
			return Decrypt(Decrypt(password));
		}
	}
}
