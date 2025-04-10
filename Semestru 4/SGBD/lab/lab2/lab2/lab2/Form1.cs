using Microsoft.Data.SqlClient;
using Microsoft.IdentityModel.Tokens;
using System.Data;
using System.Diagnostics;
using Microsoft.Extensions.Configuration;

namespace lab2
{
	public partial class Form1 : Form
	{
		private int IdSelectedParent = -1;
		private int IdSelectedChild = -1;
		private SqlConnection connection = new SqlConnection(AppConfig.GetConnectionString());
		private SqlDataAdapter dataAdapter = new SqlDataAdapter();
		private DataSet dataSet = new DataSet();

		public Form1()
		{
			InitializeComponent();
			InitializeComponentsDynamic();
			this.parentsGridView.SelectionChanged += (sender, e) =>
			{
				if (parentsGridView.SelectedRows.Count == 0)
				{
					this.IdSelectedParent = -1;
					return;
				}
				else if (parentsGridView.SelectedRows.Count == 1)
				{
					IdSelectedParent = Convert.ToInt32(parentsGridView.SelectedRows[0].Cells["id"].Value);
					this.GetChildrenButton_Click(null, null);
				}
			};
			this.childrenGridView.SelectionChanged += (sender, e) =>
			{
				if (childrenGridView.SelectedRows.Count == 0)
				{
					this.IdSelectedChild = -1;
					return;
				}
				IdSelectedChild = Convert.ToInt32(childrenGridView.SelectedRows[0].Cells["id"].Value);
				foreach (var column_name in AppConfig.GetChildTableColumnNames().Split(','))
				{
					if (column_name == "id")
						continue;
					if (column_name.Contains("id_"))
						((ComboBox)this.inputPanel.Controls[$"{column_name}TextBox"]!).SelectedValue = Convert.ToInt32(childrenGridView.SelectedRows[0].Cells[column_name].Value);

					this.inputPanel.Controls[$"{column_name}TextBox"]!.Text = childrenGridView.SelectedRows[0].Cells[column_name].Value.ToString()!;
				}
			};
			this.GetChildrenButton_Click(null, null);
			this.GetParentsButton_Click(null, null);
		}

		private void GetChildrenButton_Click(object sender, EventArgs e)
		{
			if (this.IdSelectedParent == -1)
				dataAdapter.SelectCommand = new SqlCommand("SELECT id, nume, pret, id_producator FROM MEDICAMENTE", connection);
			else
			{
				dataAdapter.SelectCommand = new SqlCommand("SELECT id, nume, pret, id_producator FROM MEDICAMENTE WHERE id_producator = @id_producator", connection);
				dataAdapter.SelectCommand.Parameters.Add("@id_producator", SqlDbType.Int).Value = this.IdSelectedParent;
			}
			if (dataSet.Tables["Drugs"] != null)
				dataSet.Tables["Drugs"]!.Clear();
			dataAdapter.Fill(dataSet, "Drugs");
			childrenGridView.DataSource = dataSet.Tables["Drugs"];

			childrenGridView.Columns["id"].Visible = false;
			childrenGridView.Columns["id_producator"].Visible = false;

			foreach (DataGridViewRow row in childrenGridView.Rows)
				row.Tag = row.Cells["id"].Value;
		}

		private void GetParentsButton_Click(object sender, EventArgs e)
		{
			dataAdapter.SelectCommand = new SqlCommand("SELECT P.id, P.nume, T.nume AS tara  FROM PRODUCATORI P INNER JOIN TARI T ON T.id = P.id_tara", connection);
			if (dataSet.Tables["Producers"] != null)
				dataSet.Tables["Producers"]!.Clear();
			dataAdapter.Fill(dataSet, "Producers");
			parentsGridView.DataSource = dataSet.Tables["Producers"];

			parentsGridView.Columns["id"].Visible = false;

			foreach (DataGridViewRow row in parentsGridView.Rows)
				row.Tag = row.Cells["id"].Value;
		}

		private void populateParentComboBox(ComboBox comboBox)
		{
			try
			{
				dataAdapter.SelectCommand = new SqlCommand("SELECT nume, id FROM PRODUCATORI", connection);
				if (dataSet.Tables["Producers"] != null)
					dataSet.Tables["Producers"]!.Clear();
				dataAdapter.Fill(dataSet, "Producers");

				comboBox.DataSource = dataSet.Tables["Producers"];
				comboBox.DisplayMember = "nume";
				comboBox.ValueMember = "id";
			}
			catch (Exception ex)
			{
				MessageBox.Show("An error occurred while populating the producer combo box: " + ex.Message);
			}
		}

		private void addChildButton_Click(object sender, EventArgs e)
		{
			try { 
				dataAdapter.InsertCommand = new SqlCommand("INSERT INTO MEDICAMENTE(nume, pret, id_producator) VALUES (@nume, @pret, @id_producator)", connection);
				foreach (var column_name in AppConfig.GetChildTableColumnNames().Split(','))
				{
					if (column_name == "id")
						continue;
					if (column_name.Contains("id_"))
						if (((ComboBox)this.inputPanel.Controls[$"{column_name}TextBox"]!).SelectedValue == null)
						{
							MessageBox.Show("Please fill in all the fields");
							return;
						}
					if (this.inputPanel.Controls[$"{column_name}TextBox"]!.Text.IsNullOrEmpty())
					{
						MessageBox.Show("Please fill in all the fields");
						return;
					}
				}
				//dataAdapter.InsertCommand.Parameters.Add("@nume", SqlDbType.VarChar).Value = nameTextBox.Text;
				//dataAdapter.InsertCommand.Parameters.Add("@pret", SqlDbType.Float).Value = float.Parse(priceTextBox.Text);
				//dataAdapter.InsertCommand.Parameters.Add("@id_producator", SqlDbType.Int).Value = parentComboBox.SelectedValue;
				connection.Open();
				dataAdapter.InsertCommand.ExecuteNonQuery();
				connection.Close();
			}
			catch (Exception ex)
			{
				MessageBox.Show("An error occurred while populating the producer combo box: " + ex.Message);
			}

			this.GetChildrenButton_Click(sender, e);
			foreach (var column_name in AppConfig.GetChildTableColumnNames().Split(','))
			{
				if (column_name == "id")
					continue;
				if (column_name.Contains("id_"))
				{
					((ComboBox)this.inputPanel.Controls[$"{column_name}TextBox"]!).SelectedValue = null;
					continue;
				}
				this.inputPanel.Controls[$"{column_name}TextBox"]!.Text = "";
			}
		}
		private void deleteChildButton_Click(object sender, EventArgs e)
		{
			if (this.IdSelectedChild == -1)
			{
				MessageBox.Show("Please select a row to delete");
				return;
			}
			try { 
				dataAdapter.DeleteCommand = new SqlCommand("DELETE FROM MEDICAMENTE WHERE id = @id", connection);
				dataAdapter.DeleteCommand.Parameters.Add("@id", SqlDbType.Int).Value = this.IdSelectedChild;
				connection.Open();
				dataAdapter.DeleteCommand.ExecuteNonQuery();
				connection.Close();
			}
			catch (Exception ex)
			{
				MessageBox.Show("An error occurred while populating the producer combo box: " + ex.Message);
			}

			this.GetChildrenButton_Click(sender, e);
		}

		private void updateChildButton_Click(object sender, EventArgs e)
		{
			if (this.IdSelectedChild == -1)
			{
				MessageBox.Show("Please select a row to delete");
				return;
			}
			//if (nameTextBox.Text.IsNullOrEmpty() || priceTextBox.Text.IsNullOrEmpty() || parentComboBox.SelectedValue == null)
			//{
			//	MessageBox.Show("Please fill in all the fields");
			//	return;
			//}

			try
			{
				//dataAdapter.UpdateCommand = new SqlCommand("UPDATE MEDICAMENTE SET nume = @nume, pret = @pret, id_producator = @id_producator WHERE id = @id", connection);
				//dataAdapter.UpdateCommand.Parameters.Add("@nume", SqlDbType.VarChar).Value = nameTextBox.Text;
				//dataAdapter.UpdateCommand.Parameters.Add("@pret", SqlDbType.Float).Value = float.Parse(priceTextBox.Text);
				//dataAdapter.UpdateCommand.Parameters.Add("@id_producator", SqlDbType.Int).Value = parentComboBox.SelectedValue;
				//dataAdapter.UpdateCommand.Parameters.Add("@id", SqlDbType.Int).Value = this.IdSelectedChild;
				connection.Open();
				dataAdapter.UpdateCommand.ExecuteNonQuery();
				connection.Close();
			}
			catch (Exception ex)
			{
				MessageBox.Show("An error occurred while populating the producer combo box: " + ex.Message);
			}
			this.GetChildrenButton_Click(sender, e);
			//nameTextBox.Text = "";
			//priceTextBox.Text = "";
		}

		private void InitializeComponentsDynamic()
		{
			GetChildrenButton.Text = AppConfig.GetChildTableName();
			addChildButton.Text = $"add {AppConfig.GetChildTableName().ToLower()}";
			getParentsButton.Text = AppConfig.GetParentTableName();
			deleteChildButton.Text = $"delete {AppConfig.GetChildTableName().ToLower()}";
			updateChildButton.Text = $"update {AppConfig.GetChildTableName().ToLower()}";
			int count = 0;
			foreach (var column_name in AppConfig.GetChildTableColumnNames().Split(','))
			{
				Debug.WriteLine(column_name);
				if (column_name == "id")
					continue;

				if (column_name.Contains("id_"))
				{
					ComboBox comboBox = new ComboBox();
					comboBox.Name = column_name + "ComboBox";
					comboBox.Location = new Point(0, (23 + 10) * count);
					comboBox.Size = new Size(inputPanel.Size.Width, 23);
					comboBox.DropDownStyle = ComboBoxStyle.DropDownList;
					this.populateParentComboBox(comboBox);
					this.inputPanel.Controls.Add(comboBox);
					count++;
					continue;
				}

				TextBox textBox = new TextBox();
				textBox.Name = column_name + "TextBox";
				textBox.PlaceholderText = char.ToUpper(column_name[0]) + column_name.Substring(1);
				textBox.Location = new Point(0, (23 + 10) * count);
				textBox.Size = new Size(inputPanel.Size.Width, 23);
				this.inputPanel.Controls.Add(textBox);
				count++;
			}

		}
	}
}
