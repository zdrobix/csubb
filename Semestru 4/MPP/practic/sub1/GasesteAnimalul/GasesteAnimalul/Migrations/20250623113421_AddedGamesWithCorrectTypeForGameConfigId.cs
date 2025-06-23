using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace GasesteAnimalul.Migrations
{
    /// <inheritdoc />
    public partial class AddedGamesWithCorrectTypeForGameConfigId : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Games_GameConfigurations_GameConfigurationId",
                table: "Games");

            migrationBuilder.DropIndex(
                name: "IX_Games_GameConfigurationId",
                table: "Games");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateIndex(
                name: "IX_Games_GameConfigurationId",
                table: "Games",
                column: "GameConfigurationId");

            migrationBuilder.AddForeignKey(
                name: "FK_Games_GameConfigurations_GameConfigurationId",
                table: "Games",
                column: "GameConfigurationId",
                principalTable: "GameConfigurations",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);
        }
    }
}
