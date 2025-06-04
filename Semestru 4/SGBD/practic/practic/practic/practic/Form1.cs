using Microsoft.Data.SqlClient;
using Microsoft.IdentityModel.Tokens;
using System.Data;
using System.Diagnostics;

namespace practic
{
	public partial class Form1 : Form
	{
		private int IdSelectedClient = -1;
		private int IdSelectedInvoice = -1;
		private SqlConnection connection = new SqlConnection("Server=DESKTOP-9PS0RE2;Database=practic_sgbd;Trusted_Connection=True;TrustServerCertificate=True;");
		private SqlDataAdapter dataAdapter = new SqlDataAdapter();
		private DataSet dataSet = new DataSet();

		public Form1()
		{
			InitializeComponent();
			this.populateClientCombobox();
			this.clientsGridView.SelectionChanged += (sender, e) =>
			{
				if (clientsGridView.SelectedRows.Count == 0)
				{
					this.IdSelectedClient = -1;
					return;
				}
				else if (clientsGridView.SelectedRows.Count == 1)
				{
					IdSelectedClient = Convert.ToInt32(clientsGridView.SelectedRows[0].Cells["id"].Value);
					this.GetInvoicesButton_Click(null, null);
				}
			};
			this.facturiGridView.SelectionChanged += (sender, e) =>
			{
				if (facturiGridView.SelectedRows.Count == 0)
				{
					this.IdSelectedInvoice = -1;
					return;
				}
				IdSelectedInvoice = Convert.ToInt32(facturiGridView.SelectedRows[0].Cells["id"].Value);
				date_textbox.Text = facturiGridView.SelectedRows[0].Cells["data_emitere"].Value.ToString();
				fordate_textbox.Text = facturiGridView.SelectedRows[0].Cells["data_scadenta"].Value.ToString();
				sum_textbox.Text = facturiGridView.SelectedRows[0].Cells["suma"].Value.ToString();
				address_textbox.Text = facturiGridView.SelectedRows[0].Cells["adresa"].Value.ToString();
				client_combobox.SelectedValue = Convert.ToInt32(facturiGridView.SelectedRows[0].Cells["id_client"].Value);
			};
			this.GetInvoicesButton_Click(null, null);
			this.GetClientsButton_Click(null, null);
		}

		private void GetInvoicesButton_Click(object sender, EventArgs e)
		{
			if (this.IdSelectedClient == -1)
				dataAdapter.SelectCommand = new SqlCommand("SELECT id, id_client, data_emitere, data_scadenta, suma, adresa FROM facturi", connection);
			else
			{
				dataAdapter.SelectCommand = new SqlCommand("SELECT id, id_client, data_emitere, data_scadenta, suma, adresa FROM facturi WHERE id_client = @id_client", connection);
				dataAdapter.SelectCommand.Parameters.Add("@id_client", SqlDbType.Int).Value = this.IdSelectedClient;
			}
			if (dataSet.Tables["Facturi"] != null)
				dataSet.Tables["Facturi"]!.Clear();
			dataAdapter.Fill(dataSet, "Facturi");
			facturiGridView.DataSource = dataSet.Tables["Facturi"];

			facturiGridView.Columns["id"].Visible = false;
			facturiGridView.Columns["id_client"].Visible = false;

			foreach (DataGridViewRow row in facturiGridView.Rows)
				row.Tag = row.Cells["id"].Value;
		}

		private void GetClientsButton_Click(object sender, EventArgs e)
		{
			dataAdapter.SelectCommand = new SqlCommand("SELECT id, nume, prenume, data_nastere, gen FROM clienti", connection);
			if (dataSet.Tables["Clienti"] != null)
				dataSet.Tables["Clienti"]!.Clear();
			dataAdapter.Fill(dataSet, "Clienti");
			clientsGridView.DataSource = dataSet.Tables["Clienti"];

			clientsGridView.Columns["id"].Visible = false;

			foreach (DataGridViewRow row in clientsGridView.Rows)
				row.Tag = row.Cells["id"].Value;
		}

		private void populateClientCombobox()
		{
			try
			{
				dataAdapter.SelectCommand = new SqlCommand("SELECT nume, id FROM clienti", connection);
				if (dataSet.Tables["Clienti"] != null)
					dataSet.Tables["Clienti"]!.Clear();
				dataAdapter.Fill(dataSet, "Clienti");

				client_combobox.DataSource = dataSet.Tables["Clienti"];
				client_combobox.DisplayMember = "nume";
				client_combobox.ValueMember = "id";
			}
			catch (Exception ex)
			{
				MessageBox.Show("An error occurred while populating the client combo box: " + ex.Message);
			}
		}

		private void addInvoiceButton_Click(object sender, EventArgs e)
		{
			try { 
				dataAdapter.InsertCommand = new SqlCommand("insert into facturi (data_emitere, data_scadenta, suma, adresa, id_client) VALUES (@data_emitere, @data_scadenta, @suma, @adresa, @id_client)", connection);
				if (date_textbox.Text.IsNullOrEmpty() || fordate_textbox.Text.IsNullOrEmpty() || client_combobox.SelectedValue == null)
				{
					MessageBox.Show("Please fill in all the fields");
					return;
				}
				dataAdapter.InsertCommand.Parameters.Add("@data_emitere", SqlDbType.Date).Value = DateOnly.Parse(date_textbox.Text);
				dataAdapter.InsertCommand.Parameters.Add("@data_scadenta", SqlDbType.Date).Value = DateOnly.Parse(fordate_textbox.Text);
				dataAdapter.InsertCommand.Parameters.Add("@suma", SqlDbType.Int).Value = int.Parse(this.sum_textbox.Text);
				dataAdapter.InsertCommand.Parameters.Add("@adresa", SqlDbType.VarChar).Value = address_textbox.Text;
				dataAdapter.InsertCommand.Parameters.Add("@id_client", SqlDbType.Int).Value = client_combobox.SelectedValue;
				connection.Open();
				dataAdapter.InsertCommand.ExecuteNonQuery();
				connection.Close();
			}
			catch (Exception ex)
			{
				MessageBox.Show("An error occurred while adding the invoice: " + ex.Message);
			}

			this.GetInvoicesButton_Click(sender, e);
			date_textbox.Text = "";
			fordate_textbox.Text = "";
			sum_textbox.Text = "";
			address_textbox.Text = "";
		}

		private void deleteInvoiceButton_Click(object sender, EventArgs e)
		{
			if (this.IdSelectedInvoice == -1)
			{
				MessageBox.Show("Please select a row to delete");
				return;
			}
			try { 
				dataAdapter.DeleteCommand = new SqlCommand("DELETE FROM facturi WHERE id = @id", connection);
				dataAdapter.DeleteCommand.Parameters.Add("@id", SqlDbType.Int).Value = this.IdSelectedInvoice;
				connection.Open();
				dataAdapter.DeleteCommand.ExecuteNonQuery();
				connection.Close();
			}
			catch (Exception ex)
			{
				MessageBox.Show("An error occurred while deleting the invoice: " + ex.Message);
			}
			date_textbox.Text = "";
			fordate_textbox.Text = "";
			sum_textbox.Text = "";
			address_textbox.Text = "";
			this.GetInvoicesButton_Click(sender, e);
		}

		private void updateInvoiceButton_Click(object sender, EventArgs e)
		{
			if (this.IdSelectedInvoice == -1)
			{
				MessageBox.Show("Please select a row to delete");
				return;
			}
			if (date_textbox.Text.IsNullOrEmpty() || fordate_textbox.Text.IsNullOrEmpty() || client_combobox.SelectedValue == null)
			{
				MessageBox.Show("Please fill in all the fields");
				return;
			}

			try
			{
				dataAdapter.UpdateCommand = new SqlCommand("UPDATE facturi SET data_emitere = @data_emitere, data_scadenta = @data_scadenta, suma = @suma, adresa = @adresa, id_client = @id_client WHERE id = @id", connection);
				dataAdapter.UpdateCommand.Parameters.Add("@data_emitere", SqlDbType.Date).Value = DateOnly.Parse(date_textbox.Text);
				dataAdapter.UpdateCommand.Parameters.Add("@data_scadenta", SqlDbType.Date).Value = DateOnly.Parse(fordate_textbox.Text);
				dataAdapter.UpdateCommand.Parameters.Add("@suma", SqlDbType.Int).Value = int.Parse(this.sum_textbox.Text);
				dataAdapter.UpdateCommand.Parameters.Add("@adresa", SqlDbType.VarChar).Value = address_textbox.Text;
				dataAdapter.UpdateCommand.Parameters.Add("@id_client", SqlDbType.Int).Value = client_combobox.SelectedValue;
				dataAdapter.UpdateCommand.Parameters.Add("@id", SqlDbType.Int).Value = this.IdSelectedInvoice;
				connection.Open();
				dataAdapter.UpdateCommand.ExecuteNonQuery();
				connection.Close();
			}
			catch (Exception ex)
			{
				MessageBox.Show("An error occurred while updating the invoice: " + ex.Message);
			}
			this.GetInvoicesButton_Click(sender, e);
			date_textbox.Text = "";
			fordate_textbox.Text = "";
			sum_textbox.Text = "";
			address_textbox.Text = "";
		}
	}
}
