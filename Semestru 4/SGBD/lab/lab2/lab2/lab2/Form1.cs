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
					IdSelectedParent = Convert.ToInt32(parentsGridView.SelectedRows[0].Cells[AppConfig.GetParentTablePrimaryKey()].Value);
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
				IdSelectedChild = Convert.ToInt32(childrenGridView.SelectedRows[0].Cells[AppConfig.GetChildTablePrimaryKey()].Value);

				foreach (var column_name in AppConfig.GetChildTableColumnNames().Split(','))
					this.inputPanel.Controls[$"{column_name}TextBox"]!.Text = childrenGridView.SelectedRows[0].Cells[column_name].Value.ToString()!;

				foreach (var column_name in AppConfig.GetChildTableForeignKey().Split(',')) 
					((ComboBox)this.inputPanel.Controls[$"{column_name}ComboBox"]!).SelectedValue = Convert.ToInt32(childrenGridView.SelectedRows[0].Cells[column_name].Value);
			};
			this.GetChildrenButton_Click(null, null);
			this.GetParentsButton_Click(null, null);
		}

		private void GetChildrenButton_Click(object sender, EventArgs e)
		{
			if (this.IdSelectedParent == -1)
				dataAdapter.SelectCommand = new SqlCommand(
										Commands.GetSelectCommand(
											AppConfig.GetChildTableName(), 
											$"{AppConfig.GetChildTablePrimaryKey()},{AppConfig.GetChildTableColumnNames()}," +
											$"{AppConfig.GetChildTableForeignKey()}")
										, connection);
			else
			{
				dataAdapter.SelectCommand = new SqlCommand(
					Commands.GetSelectCommand(AppConfig.GetChildTableName(), $"{AppConfig.GetChildTablePrimaryKey()},{AppConfig.GetChildTableColumnNames()}," +
											$"{AppConfig.GetChildTableForeignKey()}") + 
					" WHERE " + AppConfig.GetChildTableForeignKey() + " = @parent_id", connection);
				Debug.WriteLine(dataAdapter.SelectCommand.CommandText);
				dataAdapter.SelectCommand.Parameters.Add("@parent_id", SqlDbType.Int).Value = this.IdSelectedParent;
			}
			if (dataSet.Tables[AppConfig.GetChildTableName()] != null)
				dataSet.Tables[AppConfig.GetChildTableName()]!.Clear();
			dataAdapter.Fill(dataSet, AppConfig.GetChildTableName());
			childrenGridView.DataSource = dataSet.Tables[AppConfig.GetChildTableName()];

			childrenGridView.Columns[AppConfig.GetChildTableForeignKey()].Visible = false;
			childrenGridView.Columns[AppConfig.GetChildTablePrimaryKey()].Visible = false;

			foreach (DataGridViewRow row in childrenGridView.Rows)
				row.Tag = row.Cells[AppConfig.GetChildTablePrimaryKey()].Value;
		}

		private void GetParentsButton_Click(object sender, EventArgs e)
		{
			dataAdapter.SelectCommand = new SqlCommand(
				Commands.GetSelectCommand(
					AppConfig.GetParentTableName(),
					$"{AppConfig.GetParentTablePrimaryKey()},{AppConfig.GetParentTableColumnNames()}")
				, connection);
			if (dataSet.Tables[AppConfig.GetParentTableName()] != null)
				dataSet.Tables[AppConfig.GetParentTableName()]!.Clear();
			dataAdapter.Fill(dataSet, AppConfig.GetParentTableName());
			parentsGridView.DataSource = dataSet.Tables[AppConfig.GetParentTableName()];

			parentsGridView.Columns[AppConfig.GetParentTablePrimaryKey()].Visible = false;

			foreach (DataGridViewRow row in parentsGridView.Rows)
				row.Tag = row.Cells[AppConfig.GetParentTablePrimaryKey()].Value;
		}

		private void populateParentComboBox(ComboBox comboBox)
		{
			try
			{
				dataAdapter.SelectCommand = new SqlCommand(
					Commands.GetSelectCommand(
						AppConfig.GetParentTableName(), 
						$"{AppConfig.GetParentTableColumnNames()},{AppConfig.GetParentTablePrimaryKey()}")
					, connection);
				if (dataSet.Tables[AppConfig.GetParentTableName()] != null)
					dataSet.Tables[AppConfig.GetParentTableName()]!.Clear();
				dataAdapter.Fill(dataSet, AppConfig.GetParentTableName());

				comboBox.DataSource = dataSet.Tables[AppConfig.GetParentTableName()];
				comboBox.DisplayMember = AppConfig.GetParentTableColumnNames().Split(',').First();
				comboBox.ValueMember = AppConfig.GetParentTablePrimaryKey();
			}
			catch (Exception ex)
			{
				MessageBox.Show("An error occurred while populating the combo box: " + ex.Message);
			}
		}

		private void addChildButton_Click(object sender, EventArgs e)
		{
			foreach (var column_name in AppConfig.GetChildTableColumnNames().Split(','))
				if (this.inputPanel.Controls[$"{column_name}TextBox"]!.Text.IsNullOrEmpty())
				{
					MessageBox.Show("Please fill in all the fields");
					return;
				}
			foreach (var column_name in AppConfig.GetChildTableForeignKey().Split(','))
				if (((ComboBox)this.inputPanel.Controls[$"{column_name}ComboBox"]!).SelectedValue == null)
				{
					MessageBox.Show("Please fill in all the fields");
					return;
				}
			try {
				dataAdapter.InsertCommand = new SqlCommand(
					Commands.GetInsertCommand(AppConfig.GetChildTableName(),
						$"{AppConfig.GetChildTableColumnNames()},{AppConfig.GetChildTableForeignKey()}")
					, connection);

				var columns = AppConfig.GetChildTableColumnNames().Split(',');
				var types = AppConfig.GetChildTableColumnTypes().Split(',');

				for (int i = 0; i < columns.Length; i++)
				{
					switch (types[i])
					{
						case "Int":
							dataAdapter.InsertCommand.Parameters.Add($"@{columns[i]}", SqlDbType.Int).Value = int.Parse(this.inputPanel.Controls[$"{columns[i]}TextBox"]!.Text);
							break;
						case "Float":
							dataAdapter.InsertCommand.Parameters.Add($"@{columns[i]}", SqlDbType.Float).Value = float.Parse(this.inputPanel.Controls[$"{columns[i]}TextBox"]!.Text);
							break;
						case "VarChar":
							dataAdapter.InsertCommand.Parameters.Add($"@{columns[i]}", SqlDbType.VarChar).Value = this.inputPanel.Controls[$"{columns[i]}TextBox"]!.Text;
							break;
						case "Date":
							dataAdapter.InsertCommand.Parameters.Add($"@{columns[i]}", SqlDbType.Date).Value = DateTime.Parse(this.inputPanel.Controls[$"{columns[i]}TextBox"]!.Text);
							break;
					}
				}

				var foreignKeys = AppConfig.GetChildTableForeignKey().Split(',');
				var foreignKeyTypes = AppConfig.GetChildTableForeignKeyType().Split(',');

				for (int i = 0; i < foreignKeys.Length; i++)
				{
					switch (foreignKeyTypes[i])
					{
						case "Int":
							dataAdapter.InsertCommand.Parameters.Add($"@{foreignKeys[i]}", SqlDbType.Int).Value = int.Parse(((ComboBox)this.inputPanel.Controls[$"{foreignKeys[i]}ComboBox"]!).SelectedValue!.ToString()!);
							break;
						case "Float":
							dataAdapter.InsertCommand.Parameters.Add($"@{foreignKeys[i]}", SqlDbType.Float).Value = float.Parse(((ComboBox)this.inputPanel.Controls[$"{foreignKeys[i]}ComboBox"]!).SelectedValue!.ToString()!);
							break;
						case "VarChar":
							dataAdapter.InsertCommand.Parameters.Add($"@{foreignKeys[i]}", SqlDbType.VarChar).Value = ((ComboBox)this.inputPanel.Controls[$"{foreignKeys[i]}ComboBox"]!).SelectedValue!.ToString()!;
							break;
						case "Date":
							dataAdapter.InsertCommand.Parameters.Add($"@{columns[i]}", SqlDbType.Date).Value = DateTime.Parse(this.inputPanel.Controls[$"{columns[i]}TextBox"]!.Text);
							break;
					}
				}

				connection.Open();
				dataAdapter.InsertCommand.ExecuteNonQuery();
				connection.Close();
			}
			catch (Exception ex)
			{
				MessageBox.Show("An error occurred while adding: " + ex.Message);
			}

			this.GetChildrenButton_Click(sender, e);
			foreach (var column_name in AppConfig.GetChildTableColumnNames().Split(','))
				this.inputPanel.Controls[$"{column_name}TextBox"]!.Text = "";

		}
		private void deleteChildButton_Click(object sender, EventArgs e)
		{
			if (this.IdSelectedChild == -1)
			{
				MessageBox.Show("Please select a row to delete");
				return;
			}
			try { 
				dataAdapter.DeleteCommand = new SqlCommand(
					Commands.GetDeleteCommand(AppConfig.GetChildTableName(), AppConfig.GetChildTablePrimaryKey())
					, connection);

				dataAdapter.DeleteCommand.Parameters.Add($"@{AppConfig.GetChildTablePrimaryKey()}", SqlDbType.Int).Value = this.IdSelectedChild;
				connection.Open();
				dataAdapter.DeleteCommand.ExecuteNonQuery();
				connection.Close();
			}
			catch (Exception ex)
			{
				MessageBox.Show("An error occurred while deleting: " + ex.Message);
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
			foreach (var column_name in AppConfig.GetChildTableColumnNames().Split(','))
				if (this.inputPanel.Controls[$"{column_name}TextBox"]!.Text.IsNullOrEmpty())
				{
					MessageBox.Show("Please fill in all the fields");
					return;
				}
			foreach (var column_name in AppConfig.GetChildTableForeignKey().Split(','))
				if (((ComboBox)this.inputPanel.Controls[$"{column_name}ComboBox"]!).SelectedValue == null)
				{
					MessageBox.Show("Please fill in all the fields");
					return;
				}

			try
			{
				dataAdapter.UpdateCommand = new SqlCommand(
					Commands.GetUpdateCommand(AppConfig.GetChildTableName(),
						$"{AppConfig.GetChildTableColumnNames()},{AppConfig.GetChildTableForeignKey()}",
						AppConfig.GetChildTablePrimaryKey())
					, connection);


				var columns = AppConfig.GetChildTableColumnNames().Split(',');
				var types = AppConfig.GetChildTableColumnTypes().Split(',');

				for (int i = 0; i < columns.Length; i++)
				{
					switch (types[i])
					{
						case "Int":
							dataAdapter.UpdateCommand.Parameters.Add($"@{columns[i]}", SqlDbType.Int).Value = int.Parse(this.inputPanel.Controls[$"{columns[i]}TextBox"]!.Text);
							break;
						case "Float":
							dataAdapter.UpdateCommand.Parameters.Add($"@{columns[i]}", SqlDbType.Float).Value = float.Parse(this.inputPanel.Controls[$"{columns[i]}TextBox"]!.Text);
							break;
						case "VarChar":
							dataAdapter.UpdateCommand.Parameters.Add($"@{columns[i]}", SqlDbType.VarChar).Value = this.inputPanel.Controls[$"{columns[i]}TextBox"]!.Text;
							break;
					}
				}

				var foreignKeys = AppConfig.GetChildTableForeignKey().Split(',');
				var foreignKeyTypes = AppConfig.GetChildTableForeignKeyType().Split(',');

				for (int i = 0; i < foreignKeys.Length; i++)
				{
					switch (foreignKeyTypes[i])
					{
						case "Int":
							dataAdapter.UpdateCommand.Parameters.Add($"@{foreignKeys[i]}", SqlDbType.Int).Value = int.Parse(((ComboBox)this.inputPanel.Controls[$"{foreignKeys[i]}ComboBox"]!).SelectedValue!.ToString()!);
							break;
						case "Float":
							dataAdapter.UpdateCommand.Parameters.Add($"@{foreignKeys[i]}", SqlDbType.Float).Value = float.Parse(((ComboBox)this.inputPanel.Controls[$"{foreignKeys[i]}ComboBox"]!).SelectedValue!.ToString()!);
							break;
						case "VarChar":
							dataAdapter.UpdateCommand.Parameters.Add($"@{foreignKeys[i]}", SqlDbType.VarChar).Value = ((ComboBox)this.inputPanel.Controls[$"{foreignKeys[i]}ComboBox"]!).SelectedValue!.ToString()!;
							break;
					}
				}

				dataAdapter.UpdateCommand.Parameters.Add($"@{AppConfig.GetChildTablePrimaryKey()}", SqlDbType.Int).Value = this.IdSelectedChild;


				connection.Open();
				dataAdapter.UpdateCommand.ExecuteNonQuery();
				connection.Close();
			}
			catch (Exception ex)
			{
				MessageBox.Show("An error occurred while updating: " + ex.Message);
			}
			this.GetChildrenButton_Click(sender, e);
			foreach (var column_name in AppConfig.GetChildTableColumnNames().Split(','))
				this.inputPanel.Controls[$"{column_name}TextBox"]!.Text = "";
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
				TextBox textBox = new TextBox();
				textBox.Name = column_name + "TextBox";
				textBox.PlaceholderText = char.ToUpper(column_name[0]) + column_name.Substring(1);
				textBox.Location = new Point(0, (23 + 10) * count);
				textBox.Size = new Size(inputPanel.Size.Width, 23);
				this.inputPanel.Controls.Add(textBox);
				count++;
			}

			foreach (var column_name in AppConfig.GetChildTableForeignKey().Split(','))
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

		}
	}
}
