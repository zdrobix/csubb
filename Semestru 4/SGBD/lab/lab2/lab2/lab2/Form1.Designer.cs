namespace lab2
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
			GetChildrenButton = new Button();
			childrenGridView = new DataGridView();
			nameTextBox = new TextBox();
			priceTextBox = new TextBox();
			parentComboBox = new ComboBox();
			addChildButton = new Button();
			parentsGridView = new DataGridView();
			getParentsButton = new Button();
			deleteChildButton = new Button();
			updateChildButton = new Button();
			((System.ComponentModel.ISupportInitialize)childrenGridView).BeginInit();
			((System.ComponentModel.ISupportInitialize)parentsGridView).BeginInit();
			SuspendLayout();
			// 
			// GetDrugsButton
			// 
			GetChildrenButton.Location = new Point(41, 299);
			GetChildrenButton.Margin = new Padding(3, 2, 3, 2);
			GetChildrenButton.Name = "GetChildrenButton";
			GetChildrenButton.Size = new Size(322, 22);
			GetChildrenButton.TabIndex = 0;
			GetChildrenButton.Text = "drugs";
			GetChildrenButton.UseVisualStyleBackColor = true;
			GetChildrenButton.Click += GetChildrenButton_Click;
			// 
			// drugsGridView
			// 
			childrenGridView.ColumnHeadersHeightSizeMode = DataGridViewColumnHeadersHeightSizeMode.AutoSize;
			childrenGridView.Location = new Point(41, 93);
			childrenGridView.Margin = new Padding(3, 2, 3, 2);
			childrenGridView.Name = "childrenGridView";
			childrenGridView.RowHeadersWidth = 51;
			childrenGridView.Size = new Size(322, 202);
			childrenGridView.TabIndex = 1;
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
			parentComboBox.FormattingEnabled = true;
			parentComboBox.Location = new Point(405, 170);
			parentComboBox.Margin = new Padding(3, 2, 3, 2);
			parentComboBox.Name = "parentComboBox";
			parentComboBox.Size = new Size(108, 23);
			parentComboBox.TabIndex = 4;
			// 
			// addDrugButton
			// 
			addChildButton.Location = new Point(405, 247);
			addChildButton.Margin = new Padding(3, 2, 3, 2);
			addChildButton.Name = "addChildButton";
			addChildButton.Size = new Size(108, 22);
			addChildButton.TabIndex = 5;
			addChildButton.Text = "add drug";
			addChildButton.UseVisualStyleBackColor = true;
			addChildButton.Click += addChildButton_Click;
			// 
			// producerGridView
			// 
			parentsGridView.ColumnHeadersHeightSizeMode = DataGridViewColumnHeadersHeightSizeMode.AutoSize;
			parentsGridView.Location = new Point(564, 93);
			parentsGridView.Margin = new Padding(3, 2, 3, 2);
			parentsGridView.Name = "parentsGridView";
			parentsGridView.RowHeadersWidth = 51;
			parentsGridView.Size = new Size(322, 202);
			parentsGridView.TabIndex = 7;
			// 
			// getProducersButton
			// 
			getParentsButton.Location = new Point(564, 299);
			getParentsButton.Margin = new Padding(3, 2, 3, 2);
			getParentsButton.Name = "getParentsButton";
			getParentsButton.Size = new Size(322, 22);
			getParentsButton.TabIndex = 6;
			getParentsButton.Text = "producers";
			getParentsButton.UseVisualStyleBackColor = true;
			getParentsButton.Click += GetParentsButton_Click;
			// 
			// deleteDrugButton
			// 
			deleteChildButton.Location = new Point(405, 273);
			deleteChildButton.Margin = new Padding(3, 2, 3, 2);
			deleteChildButton.Name = "deleteChildButton";
			deleteChildButton.Size = new Size(108, 22);
			deleteChildButton.TabIndex = 8;
			deleteChildButton.Text = "delete drug";
			deleteChildButton.UseVisualStyleBackColor = true;
			deleteChildButton.Click += deleteChildButton_Click;
			// 
			// updateDrugButton
			// 
			updateChildButton.Location = new Point(405, 299);
			updateChildButton.Margin = new Padding(3, 2, 3, 2);
			updateChildButton.Name = "updateChildButton";
			updateChildButton.Size = new Size(108, 22);
			updateChildButton.TabIndex = 9;
			updateChildButton.Text = "update drug";
			updateChildButton.UseVisualStyleBackColor = true;
			updateChildButton.Click += updateChildButton_Click;
			// 
			// Form1
			// 
			AutoScaleDimensions = new SizeF(7F, 15F);
			AutoScaleMode = AutoScaleMode.Font;
			ClientSize = new Size(1034, 450);
			Controls.Add(updateChildButton);
			Controls.Add(deleteChildButton);
			Controls.Add(parentsGridView);
			Controls.Add(getParentsButton);
			Controls.Add(addChildButton);
			Controls.Add(parentComboBox);
			Controls.Add(priceTextBox);
			Controls.Add(nameTextBox);
			Controls.Add(childrenGridView);
			Controls.Add(GetChildrenButton);
			Margin = new Padding(3, 2, 3, 2);
			Name = "Form1";
			Text = "Form1";
			((System.ComponentModel.ISupportInitialize)childrenGridView).EndInit();
			((System.ComponentModel.ISupportInitialize)parentsGridView).EndInit();
			ResumeLayout(false);
			PerformLayout();
		}

		#endregion

		private Button GetChildrenButton;
		private DataGridView childrenGridView;
		private TextBox nameTextBox;
		private TextBox priceTextBox;
		private ComboBox parentComboBox;
		private Button addChildButton;
		private DataGridView parentsGridView;
		private Button getParentsButton;
		private Button deleteChildButton;
		private Button updateChildButton;
	}
}
