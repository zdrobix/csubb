using Microsoft.Data.SqlClient;
using Microsoft.IdentityModel.Tokens;
using System.Data;
using System.Diagnostics;

namespace lab1
{
	public partial class Form1 : Form
	{
		private BindingSource bindingSource = new BindingSource();
		private int IdSelectedProducer = -1;
		private int IdSelectedDrug = -1;
		private SqlConnection connection = new SqlConnection("Server=DESKTOP-9PS0RE2;Database=zdrobix;Trusted_Connection=True;TrustServerCertificate=True;");
		private SqlDataAdapter dataAdapter = new SqlDataAdapter();
		private DataSet dataSet = new DataSet();
		public Form1()
		{
			InitializeComponent();
			this.populateProducerComboBox();
			this.producerGridView.SelectionChanged += (sender, e) =>
			{
				if (producerGridView.SelectedRows.Count == 0)
				{
					this.IdSelectedProducer = -1;
					return;
				}
				IdSelectedProducer = Convert.ToInt32(producerGridView.SelectedRows[0].Cells["id"].Value);
				this.GetDrugsButton_Click(null, null);
			};
			this.drugsGridView.SelectionChanged += (sender, e) =>
			{
				if (drugsGridView.SelectedRows.Count == 0)
				{
					this.IdSelectedDrug = -1;
					return;
				}
				IdSelectedDrug = Convert.ToInt32(drugsGridView.SelectedRows[0].Cells["id"].Value);
				nameTextBox.Text = drugsGridView.SelectedRows[0].Cells["nume"].Value.ToString();
				priceTextBox.Text = drugsGridView.SelectedRows[0].Cells["pret"].Value.ToString();
				producerComboBox.SelectedValue = Convert.ToInt32(drugsGridView.SelectedRows[0].Cells["id_producator"].Value);
			};
			this.GetDrugsButton_Click(null, null);
			this.GetProducersButton_Click(null, null);
		}

		private void GetDrugsButton_Click(object sender, EventArgs e)
		{
			if (this.IdSelectedProducer == -1)
				dataAdapter.SelectCommand = new SqlCommand("SELECT id, nume, pret, id_producator FROM MEDICAMENTE", connection);
			else
			{
				dataAdapter.SelectCommand = new SqlCommand("SELECT id, nume, pret, id_producator FROM MEDICAMENTE WHERE id_producator = @id_producator", connection);
				dataAdapter.SelectCommand.Parameters.Add("@id_producator", SqlDbType.Int).Value = this.IdSelectedProducer;
			}
			if (dataSet.Tables["Drugs"] != null)
				dataSet.Tables["Drugs"]!.Clear();
			dataAdapter.Fill(dataSet, "Drugs");
			drugsGridView.DataSource = dataSet.Tables["Drugs"];

			drugsGridView.Columns["id"].Visible = false;
			drugsGridView.Columns["id_producator"].Visible = false;

			foreach (DataGridViewRow row in drugsGridView.Rows)
				row.Tag = row.Cells["id"].Value;
		}

		private void GetProducersButton_Click(object sender, EventArgs e)
		{
			dataAdapter.SelectCommand = new SqlCommand("SELECT P.id, P.nume, T.nume AS tara  FROM PRODUCATORI P INNER JOIN TARI T ON T.id = P.id_tara", connection);
			if (dataSet.Tables["Producers"] != null)
				dataSet.Tables["Producers"]!.Clear();
			dataAdapter.Fill(dataSet, "Producers");
			producerGridView.DataSource = dataSet.Tables["Producers"];

			producerGridView.Columns["id"].Visible = false;

			foreach (DataGridViewRow row in producerGridView.Rows)
				row.Tag = row.Cells["id"].Value;
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
			nameTextBox.Text = "";
			priceTextBox.Text = "";
		}

		private void deleteDrugButton_Click(object sender, EventArgs e)
		{
			if (this.IdSelectedDrug == -1)
			{
				MessageBox.Show("Please select a row to delete");
				return;
			}
			dataAdapter.DeleteCommand = new SqlCommand("DELETE FROM MEDICAMENTE WHERE id = @id", connection);
			dataAdapter.DeleteCommand.Parameters.Add("@id", SqlDbType.Int).Value = this.IdSelectedDrug;
			connection.Open();
			dataAdapter.DeleteCommand.ExecuteNonQuery();
			connection.Close();
			this.GetDrugsButton_Click(sender, e);
		}

		private void updateDrugButton_Click(object sender, EventArgs e)
		{
			if (this.IdSelectedDrug == -1)
			{
				MessageBox.Show("Please select a row to delete");
				return;
			}
			if (nameTextBox.Text.IsNullOrEmpty() || priceTextBox.Text.IsNullOrEmpty() || producerComboBox.SelectedValue == null)
			{
				MessageBox.Show("Please fill in all the fields");
				return;
			}
			dataAdapter.UpdateCommand = new SqlCommand("UPDATE MEDICAMENTE SET nume = @nume, pret = @pret, id_producator = @id_producator WHERE id = @id", connection);
			dataAdapter.UpdateCommand.Parameters.Add("@nume", SqlDbType.VarChar).Value = nameTextBox.Text;
			dataAdapter.UpdateCommand.Parameters.Add("@pret", SqlDbType.Float).Value = float.Parse(priceTextBox.Text);
			dataAdapter.UpdateCommand.Parameters.Add("@id_producator", SqlDbType.Int).Value = producerComboBox.SelectedValue;
			dataAdapter.UpdateCommand.Parameters.Add("@id", SqlDbType.Int).Value = this.IdSelectedDrug;
			connection.Open();
			dataAdapter.UpdateCommand.ExecuteNonQuery();
			connection.Close();
			this.GetDrugsButton_Click(sender, e);
			nameTextBox.Text = "";
			priceTextBox.Text = "";
		}
	}
}
