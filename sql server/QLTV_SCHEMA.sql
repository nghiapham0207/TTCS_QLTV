USE [QLTV]
GO
/****** Object:  User [123]    Script Date: 08/06/2022 11:38:53 PM ******/
CREATE USER [123] FOR LOGIN [123] WITH DEFAULT_SCHEMA=[dbo]
GO
/****** Object:  DatabaseRole [Độc Giả]    Script Date: 08/06/2022 11:38:53 PM ******/
CREATE ROLE [Độc Giả]
GO
/****** Object:  DatabaseRole [Thủ Thư]    Script Date: 08/06/2022 11:38:53 PM ******/
CREATE ROLE [Thủ Thư]
GO
ALTER ROLE [Độc Giả] ADD MEMBER [123]
GO
/****** Object:  Table [dbo].[CTMUONTRA]    Script Date: 08/06/2022 11:38:53 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CTMUONTRA](
	[SOPHIEU] [nchar](10) NOT NULL,
	[MAS] [nchar](10) NOT NULL,
	[NGAYTRA] [date] NULL,
	[GIAHAN] [bit] NULL,
	[PHIPHAT] [money] NULL,
	[GHICHU] [ntext] NULL,
	[MATT] [nchar](10) NULL,
 CONSTRAINT [PK_CTMUONTRA] PRIMARY KEY CLUSTERED 
(
	[SOPHIEU] ASC,
	[MAS] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
/****** Object:  Table [dbo].[CTNHAP]    Script Date: 08/06/2022 11:38:53 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CTNHAP](
	[SOPHIEUNHAP] [nchar](10) NOT NULL,
	[MADS] [nchar](10) NOT NULL,
	[SLSACHNHAP] [int] NOT NULL,
	[GIANHAP] [money] NOT NULL,
 CONSTRAINT [PK_CTNHAP] PRIMARY KEY CLUSTERED 
(
	[SOPHIEUNHAP] ASC,
	[MADS] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[CUONSACH]    Script Date: 08/06/2022 11:38:53 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CUONSACH](
	[MAS] [nchar](10) NOT NULL,
	[KIEUMUON] [nchar](3) NULL,
	[GIATL] [money] NULL,
	[MANGAN] [nchar](4) NULL,
	[SOPHIEUTL] [nchar](10) NULL,
	[MADS] [nchar](10) NOT NULL,
	[SOPHIEUNHAP] [nchar](10) NOT NULL,
	[DAMUON] [bit] NULL,
 CONSTRAINT [PK_CUONSACH] PRIMARY KEY CLUSTERED 
(
	[MAS] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[DAUSACH]    Script Date: 08/06/2022 11:38:53 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DAUSACH](
	[MADS] [nchar](10) NOT NULL,
	[TENSACH] [nvarchar](50) NOT NULL,
	[SOTRANG] [int] NULL,
	[KHOSACH] [nvarchar](3) NULL,
	[NAMXB] [int] NOT NULL,
	[MATL] [nchar](10) NOT NULL,
	[MANXB] [nchar](10) NOT NULL,
	[SL_CO_SAN] [int] NULL,
 CONSTRAINT [PK_MADS] PRIMARY KEY CLUSTERED 
(
	[MADS] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [UK_TENSACH] UNIQUE NONCLUSTERED 
(
	[TENSACH] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[DOCGIA]    Script Date: 08/06/2022 11:38:53 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DOCGIA](
	[MADG] [nchar](10) NOT NULL,
	[HO] [nvarchar](40) NOT NULL,
	[TEN] [nvarchar](10) NOT NULL,
	[PHAIDG] [nvarchar](3) NULL,
	[DIACHI] [nvarchar](50) NULL,
	[NGAYSINH] [date] NULL,
	[EMAIL] [nvarchar](40) NOT NULL,
	[SDT] [nvarchar](11) NOT NULL,
 CONSTRAINT [PK_DOCGIA] PRIMARY KEY CLUSTERED 
(
	[MADG] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[KE]    Script Date: 08/06/2022 11:38:53 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[KE](
	[MAKE] [nchar](3) NOT NULL,
	[TENKE] [nvarchar](10) NOT NULL,
 CONSTRAINT [PK_KE] PRIMARY KEY CLUSTERED 
(
	[MAKE] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [UK_TENKE] UNIQUE NONCLUSTERED 
(
	[TENKE] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[LEPHI]    Script Date: 08/06/2022 11:38:53 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[LEPHI](
	[MATT] [nchar](10) NOT NULL,
	[MADG] [nchar](10) NOT NULL,
	[NGAYDP] [date] NOT NULL,
	[PHIHANGNAM] [money] NOT NULL,
	[NGAYBD] [date] NOT NULL,
 CONSTRAINT [PK_LEPHI] PRIMARY KEY CLUSTERED 
(
	[MATT] ASC,
	[MADG] ASC,
	[NGAYDP] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[NGAN]    Script Date: 08/06/2022 11:38:53 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[NGAN](
	[MANGAN] [nchar](4) NOT NULL,
	[MAKE] [nchar](3) NOT NULL,
 CONSTRAINT [PK_NGAN] PRIMARY KEY CLUSTERED 
(
	[MANGAN] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[NHAXUATBAN]    Script Date: 08/06/2022 11:38:53 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[NHAXUATBAN](
	[MANXB] [nchar](10) NOT NULL,
	[HO] [nvarchar](40) NOT NULL,
	[TEN] [nvarchar](10) NOT NULL,
	[PHAINXB] [nvarchar](3) NULL,
	[DIACHI] [nvarchar](50) NULL,
	[NGAYSINH] [date] NULL,
	[EMAIL] [nvarchar](40) NOT NULL,
 CONSTRAINT [PK_NHAXB] PRIMARY KEY CLUSTERED 
(
	[MANXB] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[PHIEUMUONTRA]    Script Date: 08/06/2022 11:38:53 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PHIEUMUONTRA](
	[SOPHIEU] [nchar](10) NOT NULL,
	[TONGSACH] [int] NOT NULL,
	[NGAYMUON] [date] NULL,
	[NGAYHENTRA] [date] NOT NULL,
	[MADG] [nchar](10) NOT NULL,
	[MATT] [nchar](10) NOT NULL,
 CONSTRAINT [PK_SOPHIEU] PRIMARY KEY CLUSTERED 
(
	[SOPHIEU] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[PHIEUNHAP]    Script Date: 08/06/2022 11:38:53 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PHIEUNHAP](
	[SOPHIEUNHAP] [nchar](10) NOT NULL,
	[NGAYNHAP] [date] NOT NULL,
	[MATT] [nchar](10) NOT NULL,
 CONSTRAINT [PK_PHIEUNHAP] PRIMARY KEY CLUSTERED 
(
	[SOPHIEUNHAP] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[PHIEUTHANHLY]    Script Date: 08/06/2022 11:38:53 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PHIEUTHANHLY](
	[SOPHIEUTL] [nchar](10) NOT NULL,
	[NGAYTL] [date] NOT NULL,
	[MATT] [nchar](10) NOT NULL,
 CONSTRAINT [PK_SOPHIEUTL] PRIMARY KEY CLUSTERED 
(
	[SOPHIEUTL] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[SANGTAC]    Script Date: 08/06/2022 11:38:53 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[SANGTAC](
	[MATG] [nchar](10) NOT NULL,
	[MADS] [nchar](10) NOT NULL,
	[NAM_SANG_TAC] [int] NOT NULL,
 CONSTRAINT [PK_SANGTAC] PRIMARY KEY CLUSTERED 
(
	[MATG] ASC,
	[MADS] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[TACGIA]    Script Date: 08/06/2022 11:38:53 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TACGIA](
	[MATG] [nchar](10) NOT NULL,
	[HO] [nvarchar](40) NOT NULL,
	[TEN] [nvarchar](10) NOT NULL,
	[PHAITG] [nvarchar](3) NULL,
	[DIACHI] [nvarchar](50) NULL,
	[NGAYSINH] [date] NULL,
	[EMAIL] [nvarchar](40) NOT NULL,
 CONSTRAINT [PK_TACGIA] PRIMARY KEY CLUSTERED 
(
	[MATG] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[THELOAI]    Script Date: 08/06/2022 11:38:53 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[THELOAI](
	[MATL] [nchar](10) NOT NULL,
	[TENTL] [nvarchar](30) NOT NULL,
 CONSTRAINT [PK_MATL] PRIMARY KEY CLUSTERED 
(
	[MATL] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [UK_TENTL] UNIQUE NONCLUSTERED 
(
	[TENTL] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[THUTHU]    Script Date: 08/06/2022 11:38:53 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[THUTHU](
	[MATT] [nchar](10) NOT NULL,
	[HO] [nvarchar](40) NOT NULL,
	[TEN] [nvarchar](10) NOT NULL,
	[PHAITT] [nvarchar](3) NULL,
	[DIACHI] [nvarchar](50) NULL,
	[NGAYSINH] [date] NULL,
	[EMAIL] [nvarchar](40) NOT NULL,
	[NGHIVIEC] [bit] NOT NULL,
 CONSTRAINT [PK_THUTHU] PRIMARY KEY CLUSTERED 
(
	[MATT] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  View [dbo].[v_membertoadd]    Script Date: 08/06/2022 11:38:53 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create view [dbo].[v_membertoadd]
as
select name, 'User' as [type] from sys.sysusers where issqlrole = 0 and uid not in (1, 3, 4)
union
select name, 'Database Role' as [type] from sys.database_principals where is_fixed_role = 0 and type = 'R'
GO
ALTER TABLE [dbo].[CTMUONTRA] ADD  CONSTRAINT [DF_CTMUONTRA_GIAHAN_1]  DEFAULT ((0)) FOR [GIAHAN]
GO
ALTER TABLE [dbo].[CTMUONTRA] ADD  CONSTRAINT [DF_CTMUONTRA_PHIPHAT]  DEFAULT ((0)) FOR [PHIPHAT]
GO
ALTER TABLE [dbo].[CTMUONTRA] ADD  CONSTRAINT [DF_CTMUONTRA_GHICHU]  DEFAULT ('') FOR [GHICHU]
GO
ALTER TABLE [dbo].[CUONSACH] ADD  CONSTRAINT [DF_CUONSACH_KIEUMUON]  DEFAULT ('CPM') FOR [KIEUMUON]
GO
ALTER TABLE [dbo].[CUONSACH] ADD  CONSTRAINT [CK_DAMUON]  DEFAULT ((0)) FOR [DAMUON]
GO
ALTER TABLE [dbo].[DAUSACH] ADD  CONSTRAINT [DF_DAUSACH_KHOSACH]  DEFAULT (N'VỪA') FOR [KHOSACH]
GO
ALTER TABLE [dbo].[DAUSACH] ADD  DEFAULT ((0)) FOR [SL_CO_SAN]
GO
ALTER TABLE [dbo].[DOCGIA] ADD  CONSTRAINT [DF_DOCGIA_PHAI]  DEFAULT (N'Nam') FOR [PHAIDG]
GO
ALTER TABLE [dbo].[DOCGIA] ADD  CONSTRAINT [DF_DOCGIA_DIACHI]  DEFAULT (' ') FOR [DIACHI]
GO
ALTER TABLE [dbo].[DOCGIA] ADD  CONSTRAINT [DF_DOCGIA_EMAIL]  DEFAULT (' ') FOR [EMAIL]
GO
ALTER TABLE [dbo].[LEPHI] ADD  CONSTRAINT [DF_LEPHI_NGAYDP]  DEFAULT (getdate()) FOR [NGAYDP]
GO
ALTER TABLE [dbo].[NHAXUATBAN] ADD  CONSTRAINT [DF_Table_1_PHAIDG]  DEFAULT (N'Nam') FOR [PHAINXB]
GO
ALTER TABLE [dbo].[NHAXUATBAN] ADD  CONSTRAINT [DF_NHAXB_DIACHI]  DEFAULT (' ') FOR [DIACHI]
GO
ALTER TABLE [dbo].[NHAXUATBAN] ADD  CONSTRAINT [DF_NHAXB_EMAIL]  DEFAULT (' ') FOR [EMAIL]
GO
ALTER TABLE [dbo].[PHIEUTHANHLY] ADD  CONSTRAINT [DF_PHIEUTHANHLY_NGAYTL]  DEFAULT (getdate()) FOR [NGAYTL]
GO
ALTER TABLE [dbo].[TACGIA] ADD  CONSTRAINT [DF_Table_1_PHAINXB]  DEFAULT (N'Nam') FOR [PHAITG]
GO
ALTER TABLE [dbo].[TACGIA] ADD  CONSTRAINT [DF_TACGIA_DIACHI]  DEFAULT (' ') FOR [DIACHI]
GO
ALTER TABLE [dbo].[TACGIA] ADD  CONSTRAINT [DF_TACGIA_EMAIL]  DEFAULT (' ') FOR [EMAIL]
GO
ALTER TABLE [dbo].[THUTHU] ADD  CONSTRAINT [DF_Table_1_PHAI1]  DEFAULT (N'Nam') FOR [PHAITT]
GO
ALTER TABLE [dbo].[THUTHU] ADD  CONSTRAINT [DF_Table_1_DIACHI1]  DEFAULT (' ') FOR [DIACHI]
GO
ALTER TABLE [dbo].[THUTHU] ADD  CONSTRAINT [DF_THUTHU_EMAIL]  DEFAULT (' ') FOR [EMAIL]
GO
ALTER TABLE [dbo].[THUTHU] ADD  CONSTRAINT [DF_THUTHU_NGHIVIEC]  DEFAULT ((0)) FOR [NGHIVIEC]
GO
ALTER TABLE [dbo].[CTMUONTRA]  WITH CHECK ADD  CONSTRAINT [FK_CTMUONTRA_CUONSACH] FOREIGN KEY([MAS])
REFERENCES [dbo].[CUONSACH] ([MAS])
ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[CTMUONTRA] CHECK CONSTRAINT [FK_CTMUONTRA_CUONSACH]
GO
ALTER TABLE [dbo].[CTMUONTRA]  WITH CHECK ADD  CONSTRAINT [FK_CTMUONTRA_PHIEUMUONTRA1] FOREIGN KEY([SOPHIEU])
REFERENCES [dbo].[PHIEUMUONTRA] ([SOPHIEU])
ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[CTMUONTRA] CHECK CONSTRAINT [FK_CTMUONTRA_PHIEUMUONTRA1]
GO
ALTER TABLE [dbo].[CTMUONTRA]  WITH CHECK ADD  CONSTRAINT [FK_CTMUONTRA_THUTHU] FOREIGN KEY([MATT])
REFERENCES [dbo].[THUTHU] ([MATT])
GO
ALTER TABLE [dbo].[CTMUONTRA] CHECK CONSTRAINT [FK_CTMUONTRA_THUTHU]
GO
ALTER TABLE [dbo].[CTNHAP]  WITH CHECK ADD  CONSTRAINT [FK_CTNHAP_DAUSACH] FOREIGN KEY([MADS])
REFERENCES [dbo].[DAUSACH] ([MADS])
ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[CTNHAP] CHECK CONSTRAINT [FK_CTNHAP_DAUSACH]
GO
ALTER TABLE [dbo].[CTNHAP]  WITH CHECK ADD  CONSTRAINT [FK_CTNHAP_PHIEUNHAP] FOREIGN KEY([SOPHIEUNHAP])
REFERENCES [dbo].[PHIEUNHAP] ([SOPHIEUNHAP])
ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[CTNHAP] CHECK CONSTRAINT [FK_CTNHAP_PHIEUNHAP]
GO
ALTER TABLE [dbo].[CUONSACH]  WITH CHECK ADD  CONSTRAINT [FK_CUONSACH_CTNHAP] FOREIGN KEY([SOPHIEUNHAP], [MADS])
REFERENCES [dbo].[CTNHAP] ([SOPHIEUNHAP], [MADS])
GO
ALTER TABLE [dbo].[CUONSACH] CHECK CONSTRAINT [FK_CUONSACH_CTNHAP]
GO
ALTER TABLE [dbo].[CUONSACH]  WITH CHECK ADD  CONSTRAINT [FK_CUONSACH_DAUSACH] FOREIGN KEY([MADS])
REFERENCES [dbo].[DAUSACH] ([MADS])
ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[CUONSACH] CHECK CONSTRAINT [FK_CUONSACH_DAUSACH]
GO
ALTER TABLE [dbo].[CUONSACH]  WITH CHECK ADD  CONSTRAINT [FK_CUONSACH_NGAN] FOREIGN KEY([MANGAN])
REFERENCES [dbo].[NGAN] ([MANGAN])
ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[CUONSACH] CHECK CONSTRAINT [FK_CUONSACH_NGAN]
GO
ALTER TABLE [dbo].[CUONSACH]  WITH CHECK ADD  CONSTRAINT [FK_CUONSACH_PHIEUTHANHLY] FOREIGN KEY([SOPHIEUTL])
REFERENCES [dbo].[PHIEUTHANHLY] ([SOPHIEUTL])
ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[CUONSACH] CHECK CONSTRAINT [FK_CUONSACH_PHIEUTHANHLY]
GO
ALTER TABLE [dbo].[DAUSACH]  WITH CHECK ADD  CONSTRAINT [FK_DAUSACH_NHAXB] FOREIGN KEY([MANXB])
REFERENCES [dbo].[NHAXUATBAN] ([MANXB])
ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[DAUSACH] CHECK CONSTRAINT [FK_DAUSACH_NHAXB]
GO
ALTER TABLE [dbo].[DAUSACH]  WITH CHECK ADD  CONSTRAINT [FK_DAUSACH_THELOAI] FOREIGN KEY([MATL])
REFERENCES [dbo].[THELOAI] ([MATL])
ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[DAUSACH] CHECK CONSTRAINT [FK_DAUSACH_THELOAI]
GO
ALTER TABLE [dbo].[LEPHI]  WITH CHECK ADD  CONSTRAINT [FK_LEPHI_THUTHU] FOREIGN KEY([MATT])
REFERENCES [dbo].[THUTHU] ([MATT])
ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[LEPHI] CHECK CONSTRAINT [FK_LEPHI_THUTHU]
GO
ALTER TABLE [dbo].[NGAN]  WITH CHECK ADD  CONSTRAINT [FK_NGAN_KE] FOREIGN KEY([MAKE])
REFERENCES [dbo].[KE] ([MAKE])
ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[NGAN] CHECK CONSTRAINT [FK_NGAN_KE]
GO
ALTER TABLE [dbo].[PHIEUMUONTRA]  WITH CHECK ADD  CONSTRAINT [FK_PHIEUMUONTRA_DOCGIA] FOREIGN KEY([MADG])
REFERENCES [dbo].[DOCGIA] ([MADG])
ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[PHIEUMUONTRA] CHECK CONSTRAINT [FK_PHIEUMUONTRA_DOCGIA]
GO
ALTER TABLE [dbo].[PHIEUMUONTRA]  WITH CHECK ADD  CONSTRAINT [FK_PHIEUMUONTRA_THUTHU] FOREIGN KEY([MATT])
REFERENCES [dbo].[THUTHU] ([MATT])
GO
ALTER TABLE [dbo].[PHIEUMUONTRA] CHECK CONSTRAINT [FK_PHIEUMUONTRA_THUTHU]
GO
ALTER TABLE [dbo].[PHIEUNHAP]  WITH CHECK ADD  CONSTRAINT [FK_PHIEUNHAP_THUTHU] FOREIGN KEY([MATT])
REFERENCES [dbo].[THUTHU] ([MATT])
ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[PHIEUNHAP] CHECK CONSTRAINT [FK_PHIEUNHAP_THUTHU]
GO
ALTER TABLE [dbo].[PHIEUTHANHLY]  WITH CHECK ADD  CONSTRAINT [FK_PHIEUTHANHLY_THUTHU1] FOREIGN KEY([MATT])
REFERENCES [dbo].[THUTHU] ([MATT])
ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[PHIEUTHANHLY] CHECK CONSTRAINT [FK_PHIEUTHANHLY_THUTHU1]
GO
ALTER TABLE [dbo].[SANGTAC]  WITH CHECK ADD  CONSTRAINT [FK_SANGTAC_DAUSACH] FOREIGN KEY([MADS])
REFERENCES [dbo].[DAUSACH] ([MADS])
ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[SANGTAC] CHECK CONSTRAINT [FK_SANGTAC_DAUSACH]
GO
ALTER TABLE [dbo].[SANGTAC]  WITH CHECK ADD  CONSTRAINT [FK_SANGTAC_TACGIA] FOREIGN KEY([MATG])
REFERENCES [dbo].[TACGIA] ([MATG])
ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[SANGTAC] CHECK CONSTRAINT [FK_SANGTAC_TACGIA]
GO
ALTER TABLE [dbo].[CTNHAP]  WITH CHECK ADD  CONSTRAINT [CK_GIANHAP] CHECK  (([GIANHAP]>=(0)))
GO
ALTER TABLE [dbo].[CTNHAP] CHECK CONSTRAINT [CK_GIANHAP]
GO
ALTER TABLE [dbo].[CTNHAP]  WITH CHECK ADD  CONSTRAINT [CK_SLSACHNHAP] CHECK  (([SLSACHNHAP]>(0)))
GO
ALTER TABLE [dbo].[CTNHAP] CHECK CONSTRAINT [CK_SLSACHNHAP]
GO
ALTER TABLE [dbo].[DAUSACH]  WITH CHECK ADD  CONSTRAINT [CK_KHOSACH] CHECK  (([KHOSACH]=N'NHỎ' OR [KHOSACH]=N'VỪA' OR [KHOSACH]=N'LỚN'))
GO
ALTER TABLE [dbo].[DAUSACH] CHECK CONSTRAINT [CK_KHOSACH]
GO
ALTER TABLE [dbo].[DAUSACH]  WITH CHECK ADD  CONSTRAINT [CK_SL_CO_SAN] CHECK  (([SL_CO_SAN]>=(0)))
GO
ALTER TABLE [dbo].[DAUSACH] CHECK CONSTRAINT [CK_SL_CO_SAN]
GO
ALTER TABLE [dbo].[DAUSACH]  WITH CHECK ADD  CONSTRAINT [CK_SOTRANG] CHECK  (([SOTRANG]>(0)))
GO
ALTER TABLE [dbo].[DAUSACH] CHECK CONSTRAINT [CK_SOTRANG]
GO
ALTER TABLE [dbo].[DOCGIA]  WITH CHECK ADD  CONSTRAINT [CK_PHAIDG] CHECK  (([PHAIDG]=N'NỮ' OR [PHAIDG]='NAM'))
GO
ALTER TABLE [dbo].[DOCGIA] CHECK CONSTRAINT [CK_PHAIDG]
GO
ALTER TABLE [dbo].[LEPHI]  WITH CHECK ADD  CONSTRAINT [CK_NGAYBD] CHECK  (([NGAYBD]>=[NGAYDP]))
GO
ALTER TABLE [dbo].[LEPHI] CHECK CONSTRAINT [CK_NGAYBD]
GO
ALTER TABLE [dbo].[LEPHI]  WITH CHECK ADD  CONSTRAINT [CK_PHIHANGNAM] CHECK  (([PHIHANGNAM]>=(0)))
GO
ALTER TABLE [dbo].[LEPHI] CHECK CONSTRAINT [CK_PHIHANGNAM]
GO
ALTER TABLE [dbo].[NHAXUATBAN]  WITH CHECK ADD  CONSTRAINT [CK_PHAINXB] CHECK  (([PHAINXB]=N'NỮ' OR [PHAINXB]='NAM'))
GO
ALTER TABLE [dbo].[NHAXUATBAN] CHECK CONSTRAINT [CK_PHAINXB]
GO
ALTER TABLE [dbo].[PHIEUMUONTRA]  WITH CHECK ADD  CONSTRAINT [CK_NGAYHENTRA] CHECK  (([NGAYHENTRA]>=[NGAYMUON] AND datediff(day,[NGAYMUON],[NGAYHENTRA])<=(10)))
GO
ALTER TABLE [dbo].[PHIEUMUONTRA] CHECK CONSTRAINT [CK_NGAYHENTRA]
GO
ALTER TABLE [dbo].[PHIEUMUONTRA]  WITH CHECK ADD  CONSTRAINT [CK_TONGSACH] CHECK  (([TONGSACH]>=(1) AND [TONGSACH]<=(3)))
GO
ALTER TABLE [dbo].[PHIEUMUONTRA] CHECK CONSTRAINT [CK_TONGSACH]
GO
ALTER TABLE [dbo].[TACGIA]  WITH CHECK ADD  CONSTRAINT [CK_PHAITG] CHECK  (([PHAITG]=N'NỮ' OR [PHAITG]='NAM'))
GO
ALTER TABLE [dbo].[TACGIA] CHECK CONSTRAINT [CK_PHAITG]
GO
ALTER TABLE [dbo].[THUTHU]  WITH CHECK ADD  CONSTRAINT [CK_PHAITT] CHECK  (([PHAITT]=N'NỮ' OR [PHAITT]='NAM'))
GO
ALTER TABLE [dbo].[THUTHU] CHECK CONSTRAINT [CK_PHAITT]
GO
/****** Object:  StoredProcedure [dbo].[sp_backupdb_diff]    Script Date: 08/06/2022 11:38:53 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create proc [dbo].[sp_backupdb_diff]
@dbname nvarchar(128),
@path nvarchar(128)
as
begin
	declare @exec_stmt nvarchar(4000)
	set @exec_stmt = 'backup database ' + quotename(@dbname) + 
	' to disk = ' + quotename(@path, '''') + ' with differential'
	exec (@exec_stmt)
end
GO
/****** Object:  StoredProcedure [dbo].[sp_backupdb_full]    Script Date: 08/06/2022 11:38:53 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE proc [dbo].[sp_backupdb_full]
@dbname nvarchar(128),
@path nvarchar(128) 
as
begin
	declare @exec_stmt nvarchar(4000)
	set @exec_stmt = 'backup database ' + quotename(@dbname) + 
	' to disk = ' + quotename(@path, '''')
	exec (@exec_stmt)
end
GO
/****** Object:  StoredProcedure [dbo].[sp_backuplog]    Script Date: 08/06/2022 11:38:53 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create proc [dbo].[sp_backuplog]
@dbname nvarchar(128),
@path nvarchar(128)
as
begin
	declare @exec_stmt nvarchar(4000)
	set @exec_stmt = 'backup log ' + quotename(@dbname) + 
	' to disk = ' + quotename(@path, '''')
	exec (@exec_stmt)
end
GO
/****** Object:  StoredProcedure [dbo].[sp_backupsets]    Script Date: 08/06/2022 11:38:53 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE proc [dbo].[sp_backupsets]
@dbname nvarchar(128),
@physical_device_name nvarchar(128)
as
begin
select case [type]
		when 'D' then 'Full'
		when 'I' then 'Differential'
		when 'L' then 'Transaction Log'
		else [type]
	end 
as backuptype, physical_device_name, database_name, position, backup_start_date, backup_finish_date
from msdb.dbo.backupset
INNER JOIN msdb.dbo.backupmediafamily ON backupset.media_set_id = backupmediafamily.media_set_id
where 
database_name = @dbname
--physical_device_name = @physical_device_name
and backup_finish_date >= (select top 1 backup_finish_date
                             from msdb.dbo.backupset b1
                             where b1.database_name = @dbname AND
                               b1.type = 'D'
                             order by b1.backup_finish_date desc)
order by [type], backup_finish_date
end
GO
/****** Object:  StoredProcedure [dbo].[sp_getlistmember]    Script Date: 08/06/2022 11:38:53 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE proc [dbo].[sp_getlistmember]
@role_name sysname
as
begin
	select m.name as [Role Members] from sys.database_role_members rm 
	inner join sys.database_principals r on rm.role_principal_id = r.principal_id
	inner join sys.database_principals m on rm.member_principal_id = m.principal_id
	where r.name = @role_name
end
GO
/****** Object:  StoredProcedure [dbo].[sp_newLogin]    Script Date: 08/06/2022 11:38:53 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE proc [dbo].[sp_newLogin]
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

	set @exec_stmt = ''

	set @exec_stmt = 'create user '+quotename(@loginame) + ' for login '+quotename(@loginame)

	exec (@exec_stmt)
	--exec sp_grantdbaccess @loginame, @loginame
end
GO
/****** Object:  StoredProcedure [dbo].[sp_removeLogin]    Script Date: 08/06/2022 11:38:53 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create proc [dbo].[sp_removeLogin]
@loginame sysname,
@name_in_db sysname
as
begin
	exec sp_dropuser @name_in_db

	exec sp_droplogin @loginame
end
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'THỦ THƯ NHẬN TRẢ SÁCH' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'CTMUONTRA', @level2type=N'COLUMN',@level2name=N'MATT'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'SỐ LƯỢNG NHẬP' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'CTNHAP', @level2type=N'COLUMN',@level2name=N'SLSACHNHAP'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'ĐƠN GIÁ NHẬP MỘT CUỐN' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'CTNHAP', @level2type=N'COLUMN',@level2name=N'GIANHAP'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'NGÀY ĐÓNG PHÍ' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LEPHI', @level2type=N'COLUMN',@level2name=N'NGAYDP'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'NGÀY BẮT ĐẦU' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LEPHI', @level2type=N'COLUMN',@level2name=N'NGAYBD'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'SỐ PHIẾU MƯỢN TRẢ' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PHIEUMUONTRA', @level2type=N'COLUMN',@level2name=N'SOPHIEU'
GO
