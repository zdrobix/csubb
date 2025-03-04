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
			GetDrugsButton.Location = new Point(47, 399);
			GetDrugsButton.Name = "GetDrugsButton";
			GetDrugsButton.Size = new Size(300, 29);
			GetDrugsButton.TabIndex = 0;
			GetDrugsButton.Text = "drugs";
			GetDrugsButton.UseVisualStyleBackColor = true;
			GetDrugsButton.Click += GetDrugsButton_Click;
			// 
			// drugsGridView
			// 
			drugsGridView.ColumnHeadersHeightSizeMode = DataGridViewColumnHeadersHeightSizeMode.AutoSize;
			drugsGridView.Location = new Point(47, 124);
			drugsGridView.Name = "drugsGridView";
			drugsGridView.RowHeadersWidth = 51;
			drugsGridView.Size = new Size(300, 188);
			drugsGridView.TabIndex = 1;
			// 
			// nameTextBox
			// 
			nameTextBox.Location = new Point(400, 124);
			nameTextBox.Name = "nameTextBox";
			nameTextBox.PlaceholderText = "Name";
			nameTextBox.Size = new Size(123, 27);
			nameTextBox.TabIndex = 2;
			// 
			// priceTextBox
			// 
			priceTextBox.Location = new Point(400, 157);
			priceTextBox.Name = "priceTextBox";
			priceTextBox.PlaceholderText = "Price";
			priceTextBox.Size = new Size(123, 27);
			priceTextBox.TabIndex = 3;
			// 
			// producerComboBox
			// 
			producerComboBox.FormattingEnabled = true;
			producerComboBox.Location = new Point(400, 190);
			producerComboBox.Name = "producerComboBox";
			producerComboBox.Size = new Size(123, 28);
			producerComboBox.TabIndex = 4;
			// 
			// addDrugButton
			// 
			addDrugButton.Location = new Point(400, 329);
			addDrugButton.Name = "addDrugButton";
			addDrugButton.Size = new Size(123, 29);
			addDrugButton.TabIndex = 5;
			addDrugButton.Text = "add drug";
			addDrugButton.UseVisualStyleBackColor = true;
			addDrugButton.Click += addDrugButton_Click;
			// 
			// producerGridView
			// 
			producerGridView.ColumnHeadersHeightSizeMode = DataGridViewColumnHeadersHeightSizeMode.AutoSize;
			producerGridView.Location = new Point(710, 124);
			producerGridView.Name = "producerGridView";
			producerGridView.RowHeadersWidth = 51;
			producerGridView.Size = new Size(300, 188);
			producerGridView.TabIndex = 7;
			// 
			// getProducersButton
			// 
			getProducersButton.Location = new Point(710, 399);
			getProducersButton.Name = "getProducersButton";
			getProducersButton.Size = new Size(300, 29);
			getProducersButton.TabIndex = 6;
			getProducersButton.Text = "producers";
			getProducersButton.UseVisualStyleBackColor = true;
			getProducersButton.Click += getProducersButton_Click;
			// 
			// deleteDrugButton
			// 
			deleteDrugButton.Location = new Point(400, 364);
			deleteDrugButton.Name = "deleteDrugButton";
			deleteDrugButton.Size = new Size(123, 29);
			deleteDrugButton.TabIndex = 8;
			deleteDrugButton.Text = "delete drug";
			deleteDrugButton.UseVisualStyleBackColor = true;
			deleteDrugButton.Click += deleteDrugButton_Click;
			// 
			// updateDrugButton
			// 
			updateDrugButton.Location = new Point(400, 399);
			updateDrugButton.Name = "updateDrugButton";
			updateDrugButton.Size = new Size(123, 29);
			updateDrugButton.TabIndex = 9;
			updateDrugButton.Text = "update drug";
			updateDrugButton.UseVisualStyleBackColor = true;
			updateDrugButton.Click += updateDrugButton_Click;
			// 
			// Form1
			// 
			AutoScaleDimensions = new SizeF(8F, 20F);
			AutoScaleMode = AutoScaleMode.Font;
			ClientSize = new Size(1182, 600);
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
