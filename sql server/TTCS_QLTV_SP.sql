use QLTV
go
create proc sp_newLogin
@loginame nvarchar(128),
@pwd sysname = Null,
@defdb sysname = 'master',
@policy bit = 0,
@exp bit = 0,
@must_change bit = 0
as
begin
	declare @exec_stmt nvarchar(4000)
	if @pwd is null
		select @pwd = ''

	set @exec_stmt = 'create login ' + quotename(@loginame) + ' with password ='+ quotename(@pwd, '''')

	if @must_change = 1
		set @exec_stmt = @exec_stmt + ' must_change'

	set @exec_stmt = @exec_stmt + ', default_database=' + quotename(@defdb) 

	if @policy = 1
		set @exec_stmt = @exec_stmt + ', check_policy = on' 
	else
		set @exec_stmt = @exec_stmt + ', check_policy = off'

	if @exp = 1
		set @exec_stmt = @exec_stmt + ', check_expiration = on' 
	else
		set @exec_stmt = @exec_stmt + ', check_expiration = off'

	exec (@exec_stmt) --ok

	exec sp_grantdbaccess @loginame, @loginame
end

go
create proc sp_removeLogin
@loginame sysname,
@name_in_db sysname
as
begin
	exec sp_dropuser @name_in_db

	exec sp_droplogin @loginame
end