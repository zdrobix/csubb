namespace practic
{
	partial class Form1
	{
		/// <summary>
		///  Required designer variable.
		/// </summary>
		private System.ComponentModel.IContainer components = null;

		/// <summary>
		///  Clean up any resources being used.
		/// </summary>
		/// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
		protected override void Dispose(bool disposing)
		{
			if (disposing && (components != null))
			{
				components.Dispose();
			}
			base.Dispose(disposing);
		}

		#region Windows Form Designer generated code

		/// <summary>
		///  Required method for Designer support - do not modify
		///  the contents of this method with the code editor.
		/// </summary>
		private void InitializeComponent()
		{
			GetDrugsButton = new Button();
			facturiGridView = new DataGridView();
			date_textbox = new TextBox();
			fordate_textbox = new TextBox();
			client_combobox = new ComboBox();
			add_invoice_button = new Button();
			clientsGridView = new DataGridView();
			getProducersButton = new Button();
			delete_invoice_button = new Button();
			update_invoice_button = new Button();
			sum_textbox = new TextBox();
			address_textbox = new TextBox();
			((System.ComponentModel.ISupportInitialize)facturiGridView).BeginInit();
			((System.ComponentModel.ISupportInitialize)clientsGridView).BeginInit();
			SuspendLayout();
			// 
			// GetDrugsButton
			// 
			GetDrugsButton.Location = new Point(47, 399);
			GetDrugsButton.Name = "GetDrugsButton";
			GetDrugsButton.Size = new Size(483, 29);
			GetDrugsButton.TabIndex = 0;
			GetDrugsButton.Text = "invoices";
			GetDrugsButton.UseVisualStyleBackColor = true;
			GetDrugsButton.Click += GetInvoicesButton_Click;
			// 
			// facturiGridView
			// 
			facturiGridView.ColumnHeadersHeightSizeMode = DataGridViewColumnHeadersHeightSizeMode.AutoSize;
			facturiGridView.Location = new Point(47, 124);
			facturiGridView.Name = "facturiGridView";
			facturiGridView.RowHeadersWidth = 51;
			facturiGridView.Size = new Size(483, 269);
			facturiGridView.TabIndex = 1;
			// 
			// date_textbox
			// 
			date_textbox.Location = new Point(610, 124);
			date_textbox.Name = "date_textbox";
			date_textbox.PlaceholderText = "date";
			date_textbox.Size = new Size(123, 27);
			date_textbox.TabIndex = 2;
			// 
			// fordate_textbox
			// 
			fordate_textbox.Location = new Point(610, 157);
			fordate_textbox.Name = "fordate_textbox";
			fordate_textbox.PlaceholderText = "for date";
			fordate_textbox.Size = new Size(123, 27);
			fordate_textbox.TabIndex = 3;
			// 
			// client_combobox
			// 
			client_combobox.FormattingEnabled = true;
			client_combobox.Location = new Point(610, 273);
			client_combobox.Name = "client_combobox";
			client_combobox.Size = new Size(123, 28);
			client_combobox.TabIndex = 4;
			// 
			// add_invoice_button
			// 
			add_invoice_button.Location = new Point(610, 329);
			add_invoice_button.Name = "add_invoice_button";
			add_invoice_button.Size = new Size(123, 29);
			add_invoice_button.TabIndex = 5;
			add_invoice_button.Text = "add invoice";
			add_invoice_button.UseVisualStyleBackColor = true;
			add_invoice_button.Click += addInvoiceButton_Click;
			// 
			// clientsGridView
			// 
			clientsGridView.ColumnHeadersHeightSizeMode = DataGridViewColumnHeadersHeightSizeMode.AutoSize;
			clientsGridView.Location = new Point(792, 124);
			clientsGridView.Name = "clientsGridView";
			clientsGridView.RowHeadersWidth = 51;
			clientsGridView.Size = new Size(368, 269);
			clientsGridView.TabIndex = 7;
			// 
			// getProducersButton
			// 
			getProducersButton.Location = new Point(792, 399);
			getProducersButton.Name = "getProducersButton";
			getProducersButton.Size = new Size(368, 29);
			getProducersButton.TabIndex = 6;
			getProducersButton.Text = "clients";
			getProducersButton.UseVisualStyleBackColor = true;
			getProducersButton.Click += GetClientsButton_Click;
			// 
			// delete_invoice_button
			// 
			delete_invoice_button.Location = new Point(610, 364);
			delete_invoice_button.Name = "delete_invoice_button";
			delete_invoice_button.Size = new Size(123, 29);
			delete_invoice_button.TabIndex = 8;
			delete_invoice_button.Text = "delete invoice";
			delete_invoice_button.UseVisualStyleBackColor = true;
			delete_invoice_button.Click += deleteInvoiceButton_Click;
			// 
			// update_invoice_button
			// 
			update_invoice_button.Location = new Point(610, 399);
			update_invoice_button.Name = "update_invoice_button";
			update_invoice_button.Size = new Size(123, 29);
			update_invoice_button.TabIndex = 9;
			update_invoice_button.Text = "update invoice";
			update_invoice_button.UseVisualStyleBackColor = true;
			update_invoice_button.Click += updateInvoiceButton_Click;
			// 
			// sum_textbox
			// 
			sum_textbox.Location = new Point(610, 190);
			sum_textbox.Name = "sum_textbox";
			sum_textbox.PlaceholderText = "sum";
			sum_textbox.Size = new Size(123, 27);
			sum_textbox.TabIndex = 10;
			// 
			// address_textbox
			// 
			address_textbox.Location = new Point(610, 223);
			address_textbox.Name = "address_textbox";
			address_textbox.PlaceholderText = "address";
			address_textbox.Size = new Size(123, 27);
			address_textbox.TabIndex = 11;
			// 
			// Form1
			// 
			AutoScaleDimensions = new SizeF(8F, 20F);
			AutoScaleMode = AutoScaleMode.Font;
			ClientSize = new Size(1182, 600);
			Controls.Add(address_textbox);
			Controls.Add(sum_textbox);
			Controls.Add(update_invoice_button);
			Controls.Add(delete_invoice_button);
			Controls.Add(clientsGridView);
			Controls.Add(getProducersButton);
			Controls.Add(add_invoice_button);
			Controls.Add(client_combobox);
			Controls.Add(fordate_textbox);
			Controls.Add(date_textbox);
			Controls.Add(facturiGridView);
			Controls.Add(GetDrugsButton);
			Name = "Form1";
			Text = "Form1";
			((System.ComponentModel.ISupportInitialize)facturiGridView).EndInit();
			((System.ComponentModel.ISupportInitialize)clientsGridView).EndInit();
			ResumeLayout(false);
			PerformLayout();
		}

		#endregion

		private Button GetDrugsButton;
		private DataGridView facturiGridView;
		private TextBox date_textbox;
		private TextBox fordate_textbox;
		private ComboBox client_combobox;
		private Button add_invoice_button;
		private DataGridView clientsGridView;
		private Button getProducersButton;
		private Button delete_invoice_button;
		private Button update_invoice_button;
		private TextBox sum_textbox;
		private TextBox address_textbox;
	}
}
