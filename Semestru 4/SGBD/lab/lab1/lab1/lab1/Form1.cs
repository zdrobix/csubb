using Microsoft.Data.SqlClient;
using Microsoft.IdentityModel.Tokens;
using System.Data;

namespace lab1
{
	public partial class Form1 : Form
	{
		SqlConnection connection = new SqlConnection("Server=DESKTOP-9PS0RE2;Database=zdrobix;Trusted_Connection=True;TrustServerCertificate=True;");
		SqlDataAdapter dataAdapter = new SqlDataAdapter();
		DataSet dataSet = new DataSet();
		public Form1()
		{
			InitializeComponent();
			this.populateProducerComboBox();
		}

		private void GetDrugsButton_Click(object sender, EventArgs e)
		{
			dataAdapter.SelectCommand = new SqlCommand("SELECT nume, pret FROM MEDICAMENTE", connection);
			if (dataSet.Tables["Drugs"] != null)
				dataSet.Tables["Drugs"]!.Clear();
			dataAdapter.Fill(dataSet, "Drugs");
			drugsGridView.DataSource = dataSet.Tables["Drugs"];
		}

		private void getProducersButton_Click(object sender, EventArgs e)
		{
			dataAdapter.SelectCommand = new SqlCommand("SELECT P.nume, T.nume AS tara  FROM PRODUCATORI P INNER JOIN TARI T ON T.id = P.id_tara", connection);
			if (dataSet.Tables["Producers"] != null)
				dataSet.Tables["Producers"]!.Clear();
			dataAdapter.Fill(dataSet, "Producers");
			producerGridView.DataSource = dataSet.Tables["Producers"];
		}

		private void populateProducerComboBox()
		{
			try
			{
				dataAdapter.SelectCommand = new SqlCommand("SELECT nume, id FROM PRODUCATORI", connection);
				if (dataSet.Tables["Producers"] != null)
					dataSet.Tables["Producers"]!.Clear();
				dataAdapter.Fill(dataSet, "Producers");

				producerComboBox.DataSource = dataSet.Tables["Producers"];
				producerComboBox.DisplayMember = "nume";
				producerComboBox.ValueMember = "id";
			}
			catch (Exception ex)
			{
				MessageBox.Show("An error occurred while populating the producer combo box: " + ex.Message);
			}
		}

		private void addDrugButton_Click(object sender, EventArgs e)
		{
			dataAdapter.InsertCommand = new SqlCommand("INSERT INTO MEDICAMENTE(nume, pret, id_producator) VALUES (@nume, @pret, @id_producator)", connection);
			if (nameTextBox.Text.IsNullOrEmpty() || priceTextBox.Text.IsNullOrEmpty() || producerComboBox.SelectedValue == null)
			{
				MessageBox.Show("Please fill in all the fields");
				return;
			}
			dataAdapter.InsertCommand.Parameters.Add("@nume", SqlDbType.VarChar).Value = nameTextBox.Text;
			dataAdapter.InsertCommand.Parameters.Add("@pret", SqlDbType.Float).Value = float.Parse(priceTextBox.Text);
			dataAdapter.InsertCommand.Parameters.Add("@id_producator", SqlDbType.Int).Value = producerComboBox.SelectedValue;
			connection.Open();
			dataAdapter.InsertCommand.ExecuteNonQuery();
			connection.Close();
			this.GetDrugsButton_Click(sender, e);
		}

		private void deleteDrugButton_Click(object sender, EventArgs e)
		{
			if (drugsGridView.SelectedRows.Count == 0)
			{
				MessageBox.Show("Please select a row to delete");
				return;
			}
			dataAdapter.DeleteCommand = new SqlCommand("DELETE FROM MEDICAMENTE WHERE id = @id", connection);
			

		}

		private void updateDrugButton_Click(object sender, EventArgs e)
		{

		}
	}
}
