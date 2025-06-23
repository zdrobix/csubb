using GasesteAnimalul.Data;
using GasesteAnimalul.Repo.Implementation;
using GasesteAnimalul.Repo.Interface;
using Microsoft.EntityFrameworkCore;
using Serilog;
using System;

Log.Logger = new LoggerConfiguration()
	.WriteTo.File($"logs/JocAnimaleMPP.txt")
	.CreateLogger();

Log.Information("\nApplication started.\n****************\n");

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddControllers();
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();


builder.Services.AddDbContext<ApplicationDbContext>(options =>
{
	options.UseSqlServer(builder.Configuration.GetConnectionString("DefaultConnection"));
});

builder.Services.AddScoped<IUserRepository, UserRepository>();
builder.Services.AddScoped<IGameConfigRepository, GameConfigRepository>();
builder.Services.AddScoped<IGameRepository, GameRepository>();

var app = builder.Build();

if (app.Environment.IsDevelopment())
{
	app.UseSwagger();
	app.UseSwaggerUI();
}

app.UseCors(options =>
{
	options.AllowAnyHeader();
	options.AllowAnyOrigin();
	options.AllowAnyMethod();
});

app.UseHttpsRedirection();

app.UseAuthorization();

app.MapControllers();

app.Run();
