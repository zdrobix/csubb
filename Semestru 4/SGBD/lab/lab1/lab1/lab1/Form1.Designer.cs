namespace lab1
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
			drugsGridView = new DataGridView();
			nameTextBox = new TextBox();
			priceTextBox = new TextBox();
			producerComboBox = new ComboBox();
			addDrugButton = new Button();
			producerGridView = new DataGridView();
			getProducersButton = new Button();
			deleteDrugButton = new Button();
			updateDrugButton = new Button();
			((System.ComponentModel.ISupportInitialize)drugsGridView).BeginInit();
			((System.ComponentModel.ISupportInitialize)producerGridView).BeginInit();
			SuspendLayout();
			// 
			// GetDrugsButton
			// 
			GetDrugsButton.Location = new Point(41, 299);
			GetDrugsButton.Margin = new Padding(3, 2, 3, 2);
			GetDrugsButton.Name = "GetDrugsButton";
			GetDrugsButton.Size = new Size(322, 22);
			GetDrugsButton.TabIndex = 0;
			GetDrugsButton.Text = "drugs";
			GetDrugsButton.UseVisualStyleBackColor = true;
			GetDrugsButton.Click += GetDrugsButton_Click;
			// 
			// drugsGridView
			// 
			drugsGridView.ColumnHeadersHeightSizeMode = DataGridViewColumnHeadersHeightSizeMode.AutoSize;
			drugsGridView.Location = new Point(41, 93);
			drugsGridView.Margin = new Padding(3, 2, 3, 2);
			drugsGridView.Name = "drugsGridView";
			drugsGridView.RowHeadersWidth = 51;
			drugsGridView.Size = new Size(322, 202);
			drugsGridView.TabIndex = 1;
			// 
			// nameTextBox
			// 
			nameTextBox.Location = new Point(405, 93);
			nameTextBox.Margin = new Padding(3, 2, 3, 2);
			nameTextBox.Name = "nameTextBox";
			nameTextBox.PlaceholderText = "Name";
			nameTextBox.Size = new Size(108, 23);
			nameTextBox.TabIndex = 2;
			// 
			// priceTextBox
			// 
			priceTextBox.Location = new Point(405, 129);
			priceTextBox.Margin = new Padding(3, 2, 3, 2);
			priceTextBox.Name = "priceTextBox";
			priceTextBox.PlaceholderText = "Price";
			priceTextBox.Size = new Size(108, 23);
			priceTextBox.TabIndex = 3;
			// 
			// producerComboBox
			// 
			producerComboBox.FormattingEnabled = true;
			producerComboBox.Location = new Point(405, 170);
			producerComboBox.Margin = new Padding(3, 2, 3, 2);
			producerComboBox.Name = "producerComboBox";
			producerComboBox.Size = new Size(108, 23);
			producerComboBox.TabIndex = 4;
			// 
			// addDrugButton
			// 
			addDrugButton.Location = new Point(405, 247);
			addDrugButton.Margin = new Padding(3, 2, 3, 2);
			addDrugButton.Name = "addDrugButton";
			addDrugButton.Size = new Size(108, 22);
			addDrugButton.TabIndex = 5;
			addDrugButton.Text = "add drug";
			addDrugButton.UseVisualStyleBackColor = true;
			addDrugButton.Click += addDrugButton_Click;
			// 
			// producerGridView
			// 
			producerGridView.ColumnHeadersHeightSizeMode = DataGridViewColumnHeadersHeightSizeMode.AutoSize;
			producerGridView.Location = new Point(564, 93);
			producerGridView.Margin = new Padding(3, 2, 3, 2);
			producerGridView.Name = "producerGridView";
			producerGridView.RowHeadersWidth = 51;
			producerGridView.Size = new Size(322, 202);
			producerGridView.TabIndex = 7;
			// 
			// getProducersButton
			// 
			getProducersButton.Location = new Point(564, 299);
			getProducersButton.Margin = new Padding(3, 2, 3, 2);
			getProducersButton.Name = "getProducersButton";
			getProducersButton.Size = new Size(322, 22);
			getProducersButton.TabIndex = 6;
			getProducersButton.Text = "producers";
			getProducersButton.UseVisualStyleBackColor = true;
			getProducersButton.Click += GetProducersButton_Click;
			// 
			// deleteDrugButton
			// 
			deleteDrugButton.Location = new Point(405, 273);
			deleteDrugButton.Margin = new Padding(3, 2, 3, 2);
			deleteDrugButton.Name = "deleteDrugButton";
			deleteDrugButton.Size = new Size(108, 22);
			deleteDrugButton.TabIndex = 8;
			deleteDrugButton.Text = "delete drug";
			deleteDrugButton.UseVisualStyleBackColor = true;
			deleteDrugButton.Click += deleteDrugButton_Click;
			// 
			// updateDrugButton
			// 
			updateDrugButton.Location = new Point(405, 299);
			updateDrugButton.Margin = new Padding(3, 2, 3, 2);
			updateDrugButton.Name = "updateDrugButton";
			updateDrugButton.Size = new Size(108, 22);
			updateDrugButton.TabIndex = 9;
			updateDrugButton.Text = "update drug";
			updateDrugButton.UseVisualStyleBackColor = true;
			updateDrugButton.Click += updateDrugButton_Click;
			// 
			// Form1
			// 
			AutoScaleDimensions = new SizeF(7F, 15F);
			AutoScaleMode = AutoScaleMode.Font;
			ClientSize = new Size(1034, 450);
			Controls.Add(updateDrugButton);
			Controls.Add(deleteDrugButton);
			Controls.Add(producerGridView);
			Controls.Add(getProducersButton);
			Controls.Add(addDrugButton);
			Controls.Add(producerComboBox);
			Controls.Add(priceTextBox);
			Controls.Add(nameTextBox);
			Controls.Add(drugsGridView);
			Controls.Add(GetDrugsButton);
			Margin = new Padding(3, 2, 3, 2);
			Name = "Form1";
			Text = "Form1";
			((System.ComponentModel.ISupportInitialize)drugsGridView).EndInit();
			((System.ComponentModel.ISupportInitialize)producerGridView).EndInit();
			ResumeLayout(false);
			PerformLayout();
		}

		#endregion

		private Button GetDrugsButton;
		private DataGridView drugsGridView;
		private TextBox nameTextBox;
		private TextBox priceTextBox;
		private ComboBox producerComboBox;
		private Button addDrugButton;
		private DataGridView producerGridView;
		private Button getProducersButton;
		private Button deleteDrugButton;
		private Button updateDrugButton;
	}
}
