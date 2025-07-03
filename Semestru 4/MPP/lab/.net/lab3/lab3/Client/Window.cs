using Client.app.controler;
using Model.app.domain;
using System.ComponentModel;
using System.Diagnostics;

namespace Client
{
	public partial class Window : Form
	{
		private static int WINDOW_WIDTH = 1200;
		private static int WINDOW_HEIGHT = 800;

		private static int PANEL_WIDTH = 465;
		private static int PANEL_HEIGHT = 400;

		private static int SEC_PANEL_WIDTH = 300;

		private static int TEXTBOX_WIDTH = 100;
		private static int TEXTBOX_HEIGHT = 25;

		private static int BUTTON_WIDTH = 100;
		private static int BUTTON_HEIGHT = 25;

		private static int VERTICAL_SPACING = 10;
		private static int HORIZONTAL_SPACING = 10;

		private static int DATAGRID_WIDTH = 300;
		private static int DATAGRID_HEIGHT = 200;

		private static Color DARK_PURPLE = Color.FromArgb(255, 59, 30, 84);
		private static Color MEDIUM_PURPLE = Color.FromArgb(255, 155, 126, 189);
		private static Color LIGHT_PURPLE = Color.FromArgb(255, 212, 190, 228);
		private static Color GRAY = Color.FromArgb(255, 238, 238, 238);

		private static int SelectedChildId = -1;
		private static int SelectedEventId = -1;

		public Window(Controller controller)
		{
			this.Controller = controller;
			this.Controller.UpdateEventHandler += HandleUpdateEvent;
			this.InitializeComponentAuto();
			this.FormClosing += MainForm_FormClosing;
		}

		private void InitializeComponentAuto()
		{
			loginPanel = new Panel();
			usernameTextBox = new TextBox();
			passwordTextBox = new TextBox();
			loginButtonLabel = new Label();

			appPanel = new Panel();

			childrenPanel = new Panel();
			childrenSearchTextBox = new TextBox();
			childrenSearchLabelButton = new Label();
			childrenGridView = new DataGridView();
			childrenNameTextBox = new TextBox();
			childrenCnpTextBox = new TextBox();
			childrenAddLabelButton = new Label();
			childrenUpdateLabelButton = new Label();

			eventsPanel = new Panel();
			eventsSearchTextBox = new TextBox();
			eventsSearchLabelButton = new Label();
			eventsGridView = new DataGridView();
			eventsNameTextBox = new TextBox();
			eventsMinAgeTextBox = new TextBox();
			eventsMaxAgeTextBox = new TextBox();
			eventsAddLabelButton = new Label();
			eventsUpdateLabelButton = new Label();

			signupsPanel = new Panel();
			signupsSearchTextBox = new TextBox();
			signupsSearchChildLabelButton = new Label();
			signupsSearchEventLabelButton = new Label();
			signupsGridView = new DataGridView();
			signupsChildIdTextBox = new TextBox();
			signupsEventIdTextBox = new TextBox();
			signupsAddLabelButton = new Label();
			signupsDeleteLabelButton = new Label();

			SuspendLayout();

			loginPanel.Location = new Point(
				(WINDOW_WIDTH - PANEL_WIDTH) / 2,
				(WINDOW_HEIGHT - PANEL_HEIGHT) / 2
			);
			loginPanel.Name = "loginPanel";
			loginPanel.Size = new Size(PANEL_WIDTH, PANEL_HEIGHT);
			loginPanel.TabIndex = 0;
			loginPanel.BackColor = DARK_PURPLE;
			
			usernameTextBox.Location = new Point(
				(PANEL_WIDTH - TEXTBOX_WIDTH) / 2,
				loginPanel.Location.Y);
			usernameTextBox.Name = "usernameTextBox";
			usernameTextBox.PlaceholderText = "Username";
			usernameTextBox.Size = new Size(TEXTBOX_WIDTH, TEXTBOX_HEIGHT);
			usernameTextBox.TabIndex = 0;
			usernameTextBox.BackColor = GRAY;


			passwordTextBox.Location = new Point(
				usernameTextBox.Location.X,
				usernameTextBox.Location.Y + TEXTBOX_HEIGHT + VERTICAL_SPACING
			);
			passwordTextBox.Name = "passwordTextBox";
			passwordTextBox.PlaceholderText = "Password";
			passwordTextBox.Size = usernameTextBox.Size;
			passwordTextBox.TabIndex = 0;
			passwordTextBox.PasswordChar = '*';
			passwordTextBox.BackColor = GRAY;

			loginButtonLabel.Location = new Point(
				passwordTextBox.Location.X,
				passwordTextBox.Location.Y + TEXTBOX_HEIGHT + VERTICAL_SPACING
			);
			loginButtonLabel.Size = new Size(BUTTON_WIDTH, BUTTON_HEIGHT);
			loginButtonLabel.Name = "loginButton";
			loginButtonLabel.Text = "Login";
			loginButtonLabel.BackColor = GRAY;
			loginButtonLabel.TextAlign = ContentAlignment.MiddleCenter;
			loginButtonLabel.Click += (sender, e) =>
			{
				var login = this.Controller.CanLogin(usernameTextBox.Text, passwordTextBox.Text);
				if (login == null)
				{
					MessageBox.Show("Invalid login credentials.");
					return;
				}
				if (this.Controller.Login(login))
				{
					loginPanel.Visible = false;
					appPanel.Visible = true;
				}
			};

			appPanel.Location = new Point(
				(WINDOW_WIDTH - PANEL_WIDTH * 2) / 2,
				(WINDOW_HEIGHT - PANEL_HEIGHT) / 2
			);
			appPanel.Name = "appPanel";
			appPanel.Size = new Size(PANEL_WIDTH * 2, PANEL_HEIGHT);
			appPanel.BackColor = DARK_PURPLE;
			appPanel.Visible = false;

			childrenPanel.Location = new Point(0, 0);
			childrenPanel.Name = "childrenPanel";
			childrenPanel.Size = new Size(SEC_PANEL_WIDTH, PANEL_HEIGHT);
			childrenPanel.Visible = true;

			childrenSearchTextBox.Location = new Point(HORIZONTAL_SPACING, VERTICAL_SPACING);
			childrenSearchTextBox.Name = "childrenSearchTextBox";
			childrenSearchTextBox.PlaceholderText = "Search by name";
			childrenSearchTextBox.Size = new Size(2 * TEXTBOX_WIDTH, TEXTBOX_HEIGHT);

			childrenSearchLabelButton.Location = new Point(
				childrenSearchTextBox.Location.X + childrenSearchTextBox.Size.Width + HORIZONTAL_SPACING,
				childrenSearchTextBox.Location.Y
			);
			childrenSearchLabelButton.Name = "childrenSearchButton";
			childrenSearchLabelButton.Text = "Search";
			childrenSearchLabelButton.Size = new Size(BUTTON_WIDTH - HORIZONTAL_SPACING, BUTTON_HEIGHT);
			childrenSearchLabelButton.BackColor = GRAY;
			childrenSearchLabelButton.TextAlign = ContentAlignment.MiddleCenter;
			childrenSearchLabelButton.Click += (sender, e) =>
			{
				childrenGridView.ClearSelection();
				childrenGridView.CurrentCell = null;
				if (string.IsNullOrEmpty(childrenSearchTextBox.Text))
				{
					childrenGridView.DataSource = Controller
												.GetAllChildren()
												.ToList();
					return;
				}
				childrenGridView.DataSource = Controller
												.GetAllChildren()
												.Where(x => x.Name.ToLower().Contains(childrenSearchTextBox.Text.ToLower()))
												.ToList();
			};

			childrenGridView.Location = new Point(HORIZONTAL_SPACING, TEXTBOX_HEIGHT + 2 * VERTICAL_SPACING);
			childrenGridView.Size = new Size(DATAGRID_WIDTH, DATAGRID_HEIGHT);
			childrenGridView.Name = "childrenGridView";
			childrenGridView.ScrollBars = ScrollBars.Vertical;
			childrenGridView.SelectionMode = DataGridViewSelectionMode.FullRowSelect;
			childrenGridView.SelectionChanged += (sender, e) =>
			{
				if (childrenGridView.SelectedRows.Count == 0)
				{
					SelectedChildId = -1;
					return;
				}
				SelectedChildId = (int)childrenGridView.SelectedRows[0].Cells["Id"].Value;
				signupsChildIdTextBox.Text = "" + SelectedChildId;
				childrenNameTextBox.Text = childrenGridView.SelectedRows[0].Cells["Name"].Value.ToString();
				childrenCnpTextBox.Text = childrenGridView.SelectedRows[0].Cells["Cnp"].Value.ToString();
			};

			childrenNameTextBox.Location = new Point(
				childrenGridView.Location.X,
				childrenGridView.Location.Y + DATAGRID_HEIGHT + VERTICAL_SPACING
			);
			childrenNameTextBox.Name = "childrenNameTextBox";
			childrenNameTextBox.PlaceholderText = "Name";
			childrenNameTextBox.Size = new Size(DATAGRID_WIDTH, TEXTBOX_HEIGHT);

			childrenCnpTextBox.Location = new Point(
				childrenNameTextBox.Location.X ,
				childrenNameTextBox.Location.Y + TEXTBOX_HEIGHT + VERTICAL_SPACING
			);
			childrenCnpTextBox.Name = "childrenCnpTextBox";
			childrenCnpTextBox.PlaceholderText = "CNP";
			childrenCnpTextBox.Size = new Size(DATAGRID_WIDTH, TEXTBOX_HEIGHT);

			childrenAddLabelButton.Location = new Point(
				childrenCnpTextBox.Location.X,
				childrenCnpTextBox.Location.Y + TEXTBOX_HEIGHT + VERTICAL_SPACING
			);
			childrenAddLabelButton.Size = new Size(DATAGRID_WIDTH, BUTTON_HEIGHT);
			childrenAddLabelButton.Name = "childrenAddButton";
			childrenAddLabelButton.Text = "Add";
			childrenAddLabelButton.BackColor = GRAY;
			childrenAddLabelButton.TextAlign = ContentAlignment.MiddleCenter;
			childrenAddLabelButton.Click += (sender, e) =>
			{
				try
				{
					this.Controller.AddChild(childrenNameTextBox.Text, childrenCnpTextBox.Text);
				}
				catch (ArgumentException ex)
				{
					MessageBox.Show(ex.Message);
				}
				childrenNameTextBox.Text = "";
				childrenCnpTextBox.Text = "";
			};

			childrenUpdateLabelButton.Location = new Point(
				childrenAddLabelButton.Location.X,
				childrenAddLabelButton.Location.Y + BUTTON_HEIGHT + VERTICAL_SPACING
			);
			childrenUpdateLabelButton.Size = new Size(DATAGRID_WIDTH, BUTTON_HEIGHT);
			childrenUpdateLabelButton.Name = "childrenUpdateButton";
			childrenUpdateLabelButton.Text = "Update";
			childrenUpdateLabelButton.BackColor = GRAY;
			childrenUpdateLabelButton.TextAlign = ContentAlignment.MiddleCenter;
			childrenUpdateLabelButton.Click += (sender, e) =>
			{
				childrenGridView.ClearSelection();
				childrenGridView.CurrentCell = null;
				if (SelectedChildId == -1)
				{
					MessageBox.Show("No child selected.");
					return;
				}
				try
				{
					this.Controller.UpdateChild(SelectedChildId, childrenNameTextBox.Text, childrenCnpTextBox.Text);
				}
				catch (ArgumentException ex)
				{
					MessageBox.Show(ex.Message);
				}
				SelectedChildId = -1;
				childrenNameTextBox.Text = "";
				childrenCnpTextBox.Text = "";
			};

			eventsPanel.Location = new Point(SEC_PANEL_WIDTH, 0);
			eventsPanel.Name = "eventsPanel";
			eventsPanel.Size = new Size(SEC_PANEL_WIDTH, PANEL_HEIGHT);
			eventsPanel.Visible = true;

			eventsSearchTextBox.Location = new Point(
				HORIZONTAL_SPACING, VERTICAL_SPACING
			);
			eventsSearchTextBox.Name = "eventsSearchTextBox";
			eventsSearchTextBox.PlaceholderText = "Search by name";
			eventsSearchTextBox.Size = new Size(2 * TEXTBOX_WIDTH, TEXTBOX_HEIGHT);

			eventsSearchLabelButton.Location = new Point(
				eventsSearchTextBox.Location.X + eventsSearchTextBox.Size.Width + HORIZONTAL_SPACING,
				eventsSearchTextBox.Location.Y
			);
			eventsSearchLabelButton.Name = "eventsSearchButton";
			eventsSearchLabelButton.Text = "Search";
			eventsSearchLabelButton.Size = new Size(BUTTON_WIDTH - HORIZONTAL_SPACING, BUTTON_HEIGHT);
			eventsSearchLabelButton.BackColor = GRAY;
			eventsSearchLabelButton.TextAlign = ContentAlignment.MiddleCenter;
			eventsSearchLabelButton.Click += (sender, e) =>
			{
				eventsGridView.ClearSelection();
				if (string.IsNullOrEmpty(eventsSearchTextBox.Text))
				{
					eventsGridView.DataSource = Controller
												.GetAllEvents()
												.ToList();
					return;
				}
				eventsGridView.DataSource = Controller
					.GetAllEvents()
					.Where(x => x.Name.ToLower().Contains(eventsSearchTextBox.Text.ToLower()))
					.ToList();
			};

			eventsGridView.Location = new Point(HORIZONTAL_SPACING, TEXTBOX_HEIGHT + 2 * VERTICAL_SPACING);
			eventsGridView.Size = new Size(DATAGRID_WIDTH, DATAGRID_HEIGHT);
			eventsGridView.Name = "eventsGridView";
			eventsGridView.ScrollBars = ScrollBars.Vertical;
			eventsGridView.SelectionMode = DataGridViewSelectionMode.FullRowSelect;
			eventsGridView.SelectionChanged += (sender, e) =>
			{
				if (eventsGridView.SelectedRows.Count == 0)
				{
					SelectedEventId = -1;
					return;
				}
				SelectedEventId = (int)eventsGridView.SelectedRows[0].Cells["Id"].Value;
				signupsEventIdTextBox.Text = "" + SelectedEventId;
				eventsNameTextBox.Text = eventsGridView.SelectedRows[0].Cells["Name"].Value.ToString();
				eventsMinAgeTextBox.Text = eventsGridView.SelectedRows[0].Cells["MinAge"].Value.ToString();
				eventsMaxAgeTextBox.Text = eventsGridView.SelectedRows[0].Cells["MaxAge"].Value.ToString();
			};

			eventsNameTextBox.Location = new Point(
				eventsGridView.Location.X,
				eventsGridView.Location.Y + DATAGRID_HEIGHT + VERTICAL_SPACING
			);
			eventsNameTextBox.Name = "eventsNameTextBox";
			eventsNameTextBox.PlaceholderText = "Name";
			eventsNameTextBox.Size = new Size(DATAGRID_WIDTH, TEXTBOX_HEIGHT);

			eventsMinAgeTextBox.Location = new Point(
				eventsNameTextBox.Location.X,
				eventsNameTextBox.Location.Y + TEXTBOX_HEIGHT + VERTICAL_SPACING
			);
			eventsMinAgeTextBox.Name = "eventsMinAgeTextBox";
			eventsMinAgeTextBox.PlaceholderText = "Min Age";
			eventsMinAgeTextBox.Size = new Size((DATAGRID_WIDTH - HORIZONTAL_SPACING) / 2, TEXTBOX_HEIGHT);

			eventsMaxAgeTextBox.Location = new Point(
				eventsMinAgeTextBox.Location.X + eventsMinAgeTextBox.Size.Width + HORIZONTAL_SPACING,
				eventsMinAgeTextBox.Location.Y
			);
			eventsMaxAgeTextBox.Name = "eventsMaxAgeTextBox";
			eventsMaxAgeTextBox.PlaceholderText = "Max Age";
			eventsMaxAgeTextBox.Size = eventsMinAgeTextBox.Size;

			eventsAddLabelButton.Location = new Point(
				eventsMinAgeTextBox.Location.X,
				eventsMinAgeTextBox.Location.Y + TEXTBOX_HEIGHT + VERTICAL_SPACING
			);
			eventsAddLabelButton.Size = new Size(DATAGRID_WIDTH, BUTTON_HEIGHT);
			eventsAddLabelButton.Name = "eventsAddButton";
			eventsAddLabelButton.Text = "Add";
			eventsAddLabelButton.BackColor = GRAY;
			eventsAddLabelButton.TextAlign = ContentAlignment.MiddleCenter;
			eventsAddLabelButton.Click += (sender, e) =>
			{
				try
				{
					this.Controller.AddEvent(eventsNameTextBox.Text, int.Parse(eventsMinAgeTextBox.Text), int.Parse(eventsMaxAgeTextBox.Text));
				}
				catch (ArgumentException ex)
				{
					MessageBox.Show(ex.Message);
				}
				eventsNameTextBox.Text = "";
				eventsMinAgeTextBox.Text = "";
				eventsMaxAgeTextBox.Text = "";
			};

			eventsUpdateLabelButton.Location = new Point(
				eventsAddLabelButton.Location.X,
				eventsAddLabelButton.Location.Y + BUTTON_HEIGHT + VERTICAL_SPACING
			);
			eventsUpdateLabelButton.Size = new Size(DATAGRID_WIDTH, BUTTON_HEIGHT);
			eventsUpdateLabelButton.Name = "eventsUpdateButton";
			eventsUpdateLabelButton.Text = "Update";
			eventsUpdateLabelButton.BackColor = GRAY;
			eventsUpdateLabelButton.TextAlign = ContentAlignment.MiddleCenter;
			eventsUpdateLabelButton.Click += (sender, e) =>
			{
				if (SelectedEventId == -1)
				{
					MessageBox.Show("No event selected.");
					return;
				}
				try
				{
					this.Controller.UpdateEvent(SelectedEventId, eventsNameTextBox.Text, int.Parse(eventsMinAgeTextBox.Text), int.Parse(eventsMaxAgeTextBox.Text));
				}
				catch (ArgumentException ex)
				{
					MessageBox.Show(ex.Message);
				}
				SelectedEventId = -1;
				eventsGridView.ClearSelection();
				eventsNameTextBox.Text = "";
				eventsMinAgeTextBox.Text = "";
				eventsMaxAgeTextBox.Text = "";
			};

			signupsPanel.Location = new Point(2 * SEC_PANEL_WIDTH, 0);
			signupsPanel.Name = "signupsPanel";
			signupsPanel.Size = new Size(SEC_PANEL_WIDTH, PANEL_HEIGHT);
			signupsPanel.Visible = true;

			signupsSearchTextBox.Location = new Point(
				HORIZONTAL_SPACING, VERTICAL_SPACING
			);
			signupsSearchTextBox.Name = "signupsSearchTextBox";
			signupsSearchTextBox.PlaceholderText = "Search";
			signupsSearchTextBox.Size = new Size(TEXTBOX_WIDTH, TEXTBOX_HEIGHT);

			signupsSearchChildLabelButton.Location = new Point(
				signupsSearchTextBox.Location.X + signupsSearchTextBox.Size.Width + HORIZONTAL_SPACING,
				signupsSearchTextBox.Location.Y
			);
			signupsSearchChildLabelButton.Name = "signupsSearchChildButton";
			signupsSearchChildLabelButton.Text = "By child";
			signupsSearchChildLabelButton.Size = new Size(BUTTON_WIDTH - HORIZONTAL_SPACING, BUTTON_HEIGHT);
			signupsSearchChildLabelButton.BackColor = GRAY;
			signupsSearchChildLabelButton.TextAlign = ContentAlignment.MiddleCenter;
			signupsSearchChildLabelButton.Click += (sender, e) =>
			{
				if (string.IsNullOrEmpty(signupsSearchTextBox.Text))
				{
					signupsGridView.DataSource = Controller
												.GetAllSignups()
												.Select(signup => new
												{
													Child = signup.Child.Name,
													Event = signup.Event.Name
												})
												.ToList();
					return;
				}
				var signups = this.Controller
										.GetAllSignupsByChildId(int.Parse(signupsSearchTextBox.Text));
				if (signups.Count() == 0)
				{
					MessageBox.Show("No signups found.");
					return;
				}
				signupsGridView.DataSource = signups.ToList();
			};

			signupsSearchEventLabelButton.Location = new Point(
				signupsSearchChildLabelButton.Location.X + signupsSearchChildLabelButton.Size.Width + HORIZONTAL_SPACING,
				signupsSearchChildLabelButton.Location.Y
			);
			signupsSearchEventLabelButton.Name = "signupsEventIdTextBox";
			signupsSearchEventLabelButton.Text = "By event";
			signupsSearchEventLabelButton.Size = signupsSearchChildLabelButton.Size;
			signupsSearchEventLabelButton.BackColor = GRAY;
			signupsSearchEventLabelButton.TextAlign = ContentAlignment.MiddleCenter;
			signupsSearchEventLabelButton.Click += (sender, e) =>
			{
				if (string.IsNullOrEmpty(signupsSearchTextBox.Text))
				{
					signupsGridView.DataSource = Controller
												.GetAllSignups()
												.Select(signup => new
												{
													Child = signup.Child.Name,
													Event = signup.Event.Name
												})
												.ToList();
					return;
				}
				var signups = this.Controller
										.GetAllSignupsByEventId(int.Parse(signupsSearchTextBox.Text));
				if (signups!.Count() == 0)
				{
					MessageBox.Show("No signups found.");
					return;
				}
				signupsGridView.DataSource = signups!.ToList();
			};

			signupsGridView.Location = new Point(HORIZONTAL_SPACING, TEXTBOX_HEIGHT + 2 * VERTICAL_SPACING);
			signupsGridView.Size = new Size(DATAGRID_WIDTH, DATAGRID_HEIGHT);
			signupsGridView.Name = "signupsGridView";
			signupsGridView.ScrollBars = ScrollBars.Vertical;
			signupsGridView.SelectionMode = DataGridViewSelectionMode.FullRowSelect;

			signupsChildIdTextBox.Location = new Point(
				signupsGridView.Location.X,
				signupsGridView.Location.Y + DATAGRID_HEIGHT + VERTICAL_SPACING
			);
			signupsChildIdTextBox.Name = "signupsChildIdTextBox";
			signupsChildIdTextBox.PlaceholderText = "Child Id";
			signupsChildIdTextBox.Size = new Size(DATAGRID_WIDTH, TEXTBOX_HEIGHT);
			signupsChildIdTextBox.ReadOnly = true;

			signupsEventIdTextBox.Location = new Point(
				signupsChildIdTextBox.Location.X,
				signupsChildIdTextBox.Location.Y + TEXTBOX_HEIGHT + VERTICAL_SPACING
			);
			signupsEventIdTextBox.Name = "signupsEventIdTextBox";
			signupsEventIdTextBox.PlaceholderText = "Event Id";
			signupsEventIdTextBox.Size = new Size(DATAGRID_WIDTH, TEXTBOX_HEIGHT);
			signupsEventIdTextBox.ReadOnly = true;

			signupsAddLabelButton.Location = new Point(
				signupsEventIdTextBox.Location.X,
				signupsEventIdTextBox.Location.Y + TEXTBOX_HEIGHT + VERTICAL_SPACING
			);
			signupsAddLabelButton.Size = new Size(DATAGRID_WIDTH, BUTTON_HEIGHT);
			signupsAddLabelButton.Name = "signupsAddButton";
			signupsAddLabelButton.Text = "Add";
			signupsAddLabelButton.BackColor = GRAY;
			signupsAddLabelButton.TextAlign = ContentAlignment.MiddleCenter;
			signupsAddLabelButton.Click += (sender, e) =>
			{
				if (SelectedChildId == -1 || SelectedEventId == -1)
				{
					MessageBox.Show("No child or event selected.");
					return;
				}
				this.Controller.AddSignup(SelectedChildId, SelectedEventId);
				SelectedChildId = -1;
				SelectedEventId = -1;
			};

			signupsDeleteLabelButton.Location = new Point(
				signupsAddLabelButton.Location.X,
				signupsAddLabelButton.Location.Y + BUTTON_HEIGHT + VERTICAL_SPACING
			);
			signupsDeleteLabelButton.Size = new Size(DATAGRID_WIDTH, BUTTON_HEIGHT);
			signupsDeleteLabelButton.Name = "signupsDeleteButton";
			signupsDeleteLabelButton.Text = "Delete";
			signupsDeleteLabelButton.BackColor = GRAY;
			signupsDeleteLabelButton.TextAlign = ContentAlignment.MiddleCenter;


			AutoScaleDimensions = new SizeF(8F, 20F);
			AutoScaleMode = AutoScaleMode.Font;
			ClientSize = new Size(WINDOW_WIDTH, WINDOW_HEIGHT);
			this.BackColor = MEDIUM_PURPLE;

			loginPanel.Controls.Add(usernameTextBox);
			loginPanel.Controls.Add(passwordTextBox);
			loginPanel.Controls.Add(loginButtonLabel);

			childrenPanel.Controls.Add(childrenSearchTextBox);
			childrenPanel.Controls.Add(childrenSearchLabelButton);
			childrenPanel.Controls.Add(childrenGridView);
			childrenPanel.Controls.Add(childrenNameTextBox);
			childrenPanel.Controls.Add(childrenCnpTextBox);
			childrenPanel.Controls.Add(childrenAddLabelButton);
			childrenPanel.Controls.Add(childrenUpdateLabelButton);

			eventsPanel.Controls.Add(eventsSearchTextBox);
			eventsPanel.Controls.Add(eventsSearchLabelButton);
			eventsPanel.Controls.Add(eventsGridView);
			eventsPanel.Controls.Add(eventsNameTextBox);
			eventsPanel.Controls.Add(eventsMinAgeTextBox);
			eventsPanel.Controls.Add(eventsMaxAgeTextBox);
			eventsPanel.Controls.Add(eventsAddLabelButton);
			eventsPanel.Controls.Add(eventsUpdateLabelButton);

			signupsPanel.Controls.Add(signupsSearchTextBox);
			signupsPanel.Controls.Add(signupsSearchChildLabelButton);
			signupsPanel.Controls.Add(signupsSearchEventLabelButton);
			signupsPanel.Controls.Add(signupsGridView);
			signupsPanel.Controls.Add(signupsChildIdTextBox);
			signupsPanel.Controls.Add(signupsEventIdTextBox);
			signupsPanel.Controls.Add(signupsAddLabelButton);
			signupsPanel.Controls.Add(signupsDeleteLabelButton);


			appPanel.Controls.Add(childrenPanel);
			appPanel.Controls.Add(eventsPanel);
			appPanel.Controls.Add(signupsPanel);

			Controls.Add(loginPanel);
			Controls.Add(appPanel);

			Name = "Form1";
			Text = "Form1";
			Load += Form1_Load;
			ResumeLayout(false);
		}

		private Panel loginPanel;
		private TextBox usernameTextBox;
		private TextBox passwordTextBox;
		private Label loginButtonLabel;

		private Panel appPanel;


		private Panel childrenPanel;
		private TextBox childrenSearchTextBox;
		private Label childrenSearchLabelButton;
		private DataGridView childrenGridView;
		private TextBox childrenNameTextBox;
		private TextBox childrenCnpTextBox;
		private Label childrenAddLabelButton;
		private Label childrenUpdateLabelButton;

		private Panel eventsPanel;
		private TextBox eventsSearchTextBox;
		private Label eventsSearchLabelButton;
		private DataGridView eventsGridView;
		private TextBox eventsNameTextBox;
		private TextBox eventsMinAgeTextBox;
		private TextBox eventsMaxAgeTextBox;
		private Label eventsAddLabelButton;
		private Label eventsUpdateLabelButton;

		private Panel signupsPanel;
		private TextBox signupsSearchTextBox;
		private Label signupsSearchChildLabelButton;
		private Label signupsSearchEventLabelButton;
		private DataGridView signupsGridView;
		private TextBox signupsChildIdTextBox;
		private TextBox signupsEventIdTextBox;
		private Label signupsAddLabelButton;
		private Label signupsDeleteLabelButton;

		private Controller Controller;

		private void Form1_Load(object sender, EventArgs e)
		{
			childrenGridView.ClearSelection();
			childrenGridView.CurrentCell = null;
			childrenGridView.DataSource = this.Controller.GetAllChildren().ToList();
			childrenGridView.Columns["Id"].Visible = false;

			eventsGridView.ClearSelection();
			eventsGridView.CurrentCell = null;
			eventsGridView.DataSource = this.Controller.GetAllEvents().ToList();
			eventsGridView.Columns["Id"].Visible = false;
			eventsGridView.Columns["MinAge"].Visible = false;
			eventsGridView.Columns["MaxAge"].Visible = false;
			eventsGridView.Columns["Interval"].HeaderText = "Interval";

			signupsGridView.ClearSelection();
			signupsGridView.CurrentCell = null;
			signupsGridView.DataSource = this.Controller.GetAllSignups()
														.Select(signup => new 
															{
																Child = signup.Child.Name,
																Event = signup.Event.Name
															})
														.ToList();
		}

		private void MainForm_FormClosing(object? sender, FormClosingEventArgs e)
		{
			this.Controller.Logout();
		}

		private async void HandleUpdateEvent(object? sender, UpdateEventArgs e)
		{
			if (InvokeRequired)
			{
				try
				{
					Invoke(new Action<object?, UpdateEventArgs>(HandleUpdateEvent), sender, e);
				}
				catch (NullReferenceException ex)
				{
					Debug.WriteLine(ex.Message);
				}
				return;
			}
			if (e.Data == null)
				return;
			await Task.Run(() =>
			{
				switch (e.Type)
				{
					case UpdateType.ChildAdded:
						var newChild = (Child)e.Data;
						if (newChild == null)
							return;
						this.UpdateDataGridView(childrenGridView, newChild);
						break;

					case UpdateType.EventAdded:
						var newEvent = (Event)e.Data;
						if (newEvent == null)
							return;
						this.UpdateDataGridView(eventsGridView, newEvent);
						break;

					case UpdateType.SignupAdded:
						var newSignup = (Signup)e.Data;
						if (newSignup == null)
							return;
						this.UpdateDataGridView(signupsGridView, newSignup);
						break;
					default:
						break;
				}
			});
		}

		private void UpdateDataGridView<T>(DataGridView gridView, T newItem)
		{
			if (InvokeRequired)
			{
				Invoke(new Action<DataGridView, T>(UpdateDataGridView), gridView, newItem);
				return;
			}

			var bindingList = gridView.DataSource as BindingList<T>;
			if (bindingList == null)
			{
				var existing = gridView.DataSource as List<T> ?? new List<T>();
				bindingList = new BindingList<T>(existing);
				gridView.DataSource = bindingList;
			}

			if (!bindingList.Contains(newItem))
			{
				bindingList.Add(newItem);
			}
		}
	}
}
