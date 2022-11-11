-- phpMyAdmin SQL Dump
-- version 4.9.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Tempo de geração: 12-Jun-2020 às 12:49
-- Versão do servidor: 5.6.47
-- versão do PHP: 7.3.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `quasarpt_isctelisboa`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `point_of_interest`
--

CREATE TABLE `point_of_interest` (
  `point_id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  `point_name` varchar(100) NOT NULL,
  `door_number` int(11) NOT NULL,
  `postal_code` varchar(10) NOT NULL,
  `address` varchar(200) NOT NULL,
  `longitude` double NOT NULL,
  `latitude` double NOT NULL,
  `altitude` double NOT NULL,
  `opens_hours` int(11) NOT NULL,
  `closes_hours` int(11) NOT NULL,
  `contact_mobile` int(11) NOT NULL,
  `contact_landline` int(11) NOT NULL,
  `point_url` varchar(20000) NOT NULL,
  `image_url` varchar(500) NOT NULL,
  `sustainability` int(11) NOT NULL,
  `price` double NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `point_of_interest`
--

INSERT INTO `point_of_interest` (`point_id`, `category_id`, `point_name`, `door_number`, `postal_code`, `address`, `longitude`, `latitude`, `altitude`, `opens_hours`, `closes_hours`, `contact_mobile`, `contact_landline`, `point_url`, `image_url`, `sustainability`, `price`) VALUES
(13, 7, 'Castelo de São Jorge', 1, '971', 'Rua de Santa Cruz do Castelo', -9.1334762, 38.7139092, 90, 9, 18, 0, 218800620, 'http://castelodesaojorge.pt/pt/', 'https://upload.wikimedia.org/wikipedia/commons/thumb/1/17/Castelo_de_S%C3%A3o_Jorge_%2810251035013%29.jpg/1024px-Castelo_de_S%C3%A3o_Jorge_%2810251035013%29.jpg', 3, 10),
(14, 2, 'Capela Nossa Senhora da Saúde', 1, '854', 'Rua da Mouraria', -9.13569194, 38.71586278, 25, 0, 0, 0, 218862093, 'na', 'https://www.guiadacidade.pt/assets/capas_poi/capa_24149.jpg', 5, 0),
(15, 5, 'Tasquinha A Vaidosa', 58, '704', 'Rua do Terreirinho ', -9.13441912, 38.71695138, 37, 9, 22, 0, 218867337, 'https://www.zomato.com/pt/grande-lisboa/tasquinha-a-vaidosa-mouraria-lisboa', 'https://upload.wikimedia.org/wikipedia/commons/thumb/0/07/Pal%C3%A1cio_da_Rosa%2C_Lisboa%2C_Portugal_02.jpg/1024px-Pal%C3%A1cio_da_Rosa%2C_Lisboa%2C_Portugal_02.jpg', 2, 7.5),
(16, 7, 'Palácio da Rosa', 1, '976', 'Largo da Rosa', -9.1346331, 38.714776, 55, 0, 0, 0, 0, 'na', 'https://upload.wikimedia.org/wikipedia/commons/thumb/0/07/Pal%C3%A1cio_da_Rosa%2C_Lisboa%2C_Portugal_02.jpg/1024px-Pal%C3%A1cio_da_Rosa%2C_Lisboa%2C_Portugal_02.jpg', 1, 0),
(17, 1, 'Pérola do Rossio', 105, '898', 'Praça Dom Pedro IV', -9.13862817, 38.71356062, 19, 10, 19, 0, 213420744, 'https://www.peroladorossio.pt/', 'https://media.timeout.com/images/104095794/630/472/image.jpg', 1, 0),
(18, 6, 'Casa dos Bicos - Fundação José Saramago', 10, '965', 'Rua dos Bacalhoeiros ', -9.1326586, 38.709058, 18, 10, 18, 0, 218802040, 'https://www.josesaramago.org/onde-estamos/', 'https://upload.wikimedia.org/wikipedia/commons/thumb/2/25/CasaBicos1.jpg/800px-CasaBicos1.jpg', 2, 3),
(19, 3, 'Miradouro do Recolhimento', 1, '971', 'R. do Recolhimento', -9.13171991, 38.71267494, 81, 10, 19, 915225592, 0, 'https://www.lisbonlux.com/lisbon/miradouro-do-recolhimento.html', 'https://www.lisbonlux.com/images/lisbon/miradouro-recolhimento.jpg', 5, 0),
(20, 3, 'Miradouro de Santa Luzia', 1, '613', 'Largo de santa Luzia', -9.13034395, 38.71159619, 51, 0, 0, 0, 0, 'https://www.lisbonlux.com/lisbon/miradouro-de-santa-luzia.html', 'https://www.lisbonlux.com/images/lisbon/miradouro-de-santa-luzia.jpg', 1, 0),
(22, 7, 'Elevador da Bica', 234, '1091', 'Rua de S. Paulo 234', -9.14669643, 38.70858166, 22, 7, 21, 0, 0, 'http://www.carris.pt/pt/ascensores-e-elevador/', 'https://upload.wikimedia.org/wikipedia/commons/thumb/b/bd/Lisboa_-_Ascensor_da_Bica_%281%29.jpg/235px-Lisboa_-_Ascensor_da_Bica_%281%29.jpg', 5, 3.7),
(23, 7, 'Elevador de Santa Justa', 1, '1090', 'Rua do Ouro', -9.1394235, 38.71212908, 32, 8, 21, 0, 214138679, 'http://www.carris.pt/pt/ascensores-e-elevador/', 'https://upload.wikimedia.org/wikipedia/commons/thumb/e/ef/Lisbon_%28Lisboa%29_historic_elevator_Santa_Justa_Luca_Galuzzi_2006.jpg/800px-Lisbon_%28Lisboa%29_historic_elevator_Santa_Justa_Luca_Galuzzi_2006.jpg', 2, 5.15),
(24, 7, 'Elevador da Glória', 6, '1249', 'Calçada da Glória, 1250-001 Lisboa', -9.14335103, 38.71539594, 42, 7, 24, 0, 213500115, 'http://www.carris.pt/pt/ascensores-e-elevador/', 'https://upload.wikimedia.org/wikipedia/commons/thumb/1/1c/Tram_in_Lisboa_pic-005.JPG/800px-Tram_in_Lisboa_pic-005.JPG', 3, 3.7),
(25, 2, 'Igreja de São Nicolau', 1, '482', 'R. da Vitória Igreja', -9.13669238, 38.71093597, 26, 0, 0, 0, 218879549, 'http://www.paroquiasaonicolau.pt/', '', 3, 0),
(26, 1, 'Nicolau Café', 17, '553', 'R. São Nicolau ', -9.1364516, 38.7105554, 27, 9, 20, 0, 218860312, 'http://www.ilovenicolau.com/en/', 'http://www.ilovenicolau.com/media/filer_public_thumbnails/filer_public/9a/46/9a466384-3c20-4563-8497-235e34f9161e/ilovenicolau_slide5.jpg__1140x760_q85_crop-center_subsampling-2.jpg', 1, 0),
(27, 8, 'Praça do Comércio', 1, '952', 'Praça do Comércio', -9.1364489, 38.707532, 11, 0, 0, 0, 0, 'https://www.lisbonlux.com/lisbon/praca-do-comercio.html', 'https://nit.pt/wp-content/uploads/2018/07/f2da8f1c626b617f3061de5ae9ef8e43-754x394.jpg', 5, 0),
(28, 3, 'Cais das Colunas', 1, '952', 'Praça do Comércio', -9.1360868, 38.70661524, 2, 0, 0, 0, 0, 'na', 'https://pt.wikipedia.org/wiki/Cais_das_Colunas#/media/File:Lisbon_With_Langon_-_01_(3466311681).jpg', 2, 0),
(29, 6, 'Museu de Lisboa - Torreão Poente', 1, '1017', 'Praça do Comércio', -9.13726697, 38.7067785, 8, 10, 18, 0, 217513200, 'http://www.museudelisboa.pt/equipamentos/torreao-poente.html', 'http://www.museudelisboa.pt/fileadmin/museu_lisboa/_processed_/csm_torreao_poente_banner_c49a31261f.png', 3, 0),
(30, 6, 'Museu do Design e da Moda', 24, '1047', 'Rua Augusta', -9.1369361, 38.7090582, 20, 10, 18, 0, 218886117, 'http://www.mude.pt/', 'http://www.mude.pt/https://www.playocean.net/i/portugal/lisboa/museus/mude-museu-design-moda/mude-museu-design-moda-2.jpg', 5, 0),
(31, 3, 'Arco da Rua Augusta', 2, '1047', 'Rua Augusta', -9.13682345, 38.70839262, 14, 0, 0, 0, 0, 'na', 'http://www.cm-lisboa.pt/uploads/pics/LFP_Arco_Rua_Augusta_inauguracao_10.jpg', 1, 0),
(32, 2, 'Igreja da Nossa Senhora da Conceição Velha', 108, '1084', 'Rua da Alfândega', -9.13423873, 38.70896476, 20, 0, 0, 0, 218870202, 'na', 'https://c1.staticflickr.com/6/5583/14604202910_83294ee3ab_b.jpg', 3, 0),
(33, 8, 'Praça do Rossio', 1, '900', 'Praça Dom Pedro IV', -9.1396806, 38.7137188, 24, 0, 0, 0, 0, 'na', 'https://www.guiadacidade.pt/assets/capas_poi/capa_16392.jpg', 1, 0),
(34, 6, 'O Mundo Fantástico da Sardinha Portuguesa', 39, '900', 'Praça Dom Pedro IV', -9.1393499, 38.7144703, 22, 10, 20, 0, 211349044, 'https://www.mundofantasticodasardinha.pt/', 'https://media-cdn.tripadvisor.com/media/photo-s/0d/ca/f2/97/mundo-fantastico-da-sardinha.jpg', 4, 0),
(35, 1, 'Nicola Café', 24, '1109', 'Praça Dom Pedro IV', -9.13967177, 38.71321458, 26, 8, 24, 0, 213460579, 'na', 'https://u.tfstatic.com/restaurant_photos/969/200969/169/612/cafe-nicola-esplanada-64e35.jpg', 3, 0),
(36, 8, 'Praça da Figueira', 1, '903', 'Praça da Figueira ', -9.13786591, 38.71372417, 22, 0, 0, 0, 0, 'na', 'https://nit.pt/wp-content/uploads/2018/02/d9b37aa6b6a5896e24e9a89aefc6d1b5-754x394.jpg', 4, 0),
(37, 2, 'Igreja da Madalena', 1, '1090', 'Largo Madalena', -9.13476212, 38.7101015, 28, 9, 7, 0, 218870987, 'https://www.guiadacidade.pt/pt/poi-igreja-da-madalena-17837', 'https://upload.wikimedia.org/wikipedia/commons/thumb/8/8d/Igreja_da_Madalena2037.JPG/220px-Igreja_da_Madalena2037.JPG', 2, 0),
(38, 2, 'Igreja de Santo António de Lisboa', 1, '699', 'Largo de Santo António da Sé', -9.13401066, 38.70999347, 31, 8, 19, 0, 218869145, 'http://stoantoniolisboa.com/', 'https://www.lisbonlux.com/images/magazine/igreja-de-santo-antonio.jpg', 2, 0),
(39, 7, 'Sé de Lisboa', 1, '515', 'Largo da Sé', -9.13340813, 38.70980306, 31, 10, 17, 0, 218866752, 'https://www.lisboa.net/catedral-se-de-lisboa', 'http://www.cm-lisboa.pt/uploads/pics/tt_address/se_ALA7099.jpg', 3, 0),
(40, 2, 'Igreja de São Domingos', 1, '830', 'Largo São Domingos', -9.13875324, 38.71470839, 25, 8, 19, 0, 213428275, 'https://www.guiadacidade.pt/pt/poi-igreja-de-sao-domingos-16603', 'https://www.guiadacidade.pt/pim/phpThumb.php?src=http://www.guiadacidade.pt/foto2/data/media/54/PalcioIndependnciaLisboa_001_resize.JPG&w=870&zc=1', 2, 0),
(41, 1, 'Hospital de Bonecas', 7, '860', 'Praça da Figueira', -9.13776636, 38.71417458, 22, 10, 19, 0, 213428574, 'http://www.hospitaldebonecas.com/', 'http://www.cm-lisboa.pt/uploads/pics/tt_address/hospital-de-bonecas-1818.jpg', 1, 0),
(42, 1, 'Manteigaria Silva', 1, '1074', 'Rua Dom Antão De Almada Lisboa', -9.13876112, 38.71406446, 21, 9, 20, 0, 213424905, 'https://www.manteigariasilva.pt/pt/', 'https://d1u4v68pfiv879.cloudfront.net/media/2092/d-data-hosts-gmc-devfogmcblissapplicationscom-content-content-imagens-manteigaria-silva-destaques-manteigaria-silva_635x400.jpg', 2, 0),
(43, 6, 'Museu Arqueológico do Carmo', 1, '1108', 'Largo do Carmo', -9.14063627, 38.71190513, 47, 10, 19, 0, 213478629, 'http://www.museuarqueologicodocarmo.pt/', 'http://www.museuarqueologicodocarmo.pt/assets/img/portfolio/work1.jpg', 5, 0),
(44, 6, 'Museu Nacional de Arte Contemporânea do Chiado ', 13, '756', 'Rua Capelo', -9.14102261, 38.70968009, 45, 10, 18, 0, 213432148, 'http://www.museuartecontemporanea.gov.pt/pt', 'http://www.cm-lisboa.pt/uploads/pics/tt_address/Museu-Chiado-2047-R.-Serpa-Pinto.jpg', 1, 0),
(126, 2, 'Convento de São Francisco', 4, '1249-058', 'Largo da Academia Nacional de Belas Artes ,  Lisboa', -9.140173, 38.708848, 0, 0, 0, 0, 0, 'Foi conhecido como a Cidade de São Francisco, devido à grande área que ocupava. A localização dos exércitos de cruzados na conquista de Lisboa, proporcionou a construção da Ermida de Nossa Senhora dos Mártires, fundada por D. Afonso Henriques. Em 1217, o ermitério de frades Franciscanos cresceu e em 1246 tornou-se no convento de São Francisco, passando a comportar também um hospital. O prestígio dos franciscanos entre o povo e a Coroa fazia com que até os reis, como D. Sancho II, vestissem o seu hábito. Após a construção da muralha Fernandina (1375), o Convento passou a estar integrado na cidade. Para além de convento, templo e hospital, era também local de albergue, possuindo uma horta. Durante o século XVII, realizaram-se neste Convento várias reuniões das Cortes do país.\r\nApós o incêndio em 1708 (com novo incendio em 1741) o Convento foi reconstruído. Porém, no dia 1 de novembro de 1755, o terramoto destrui o Convento e o incêndio que então destruiu grande parte da cidade derreteu as alfaias de prata e os 9000 livros da livraria arderam. Nessa ocasião, terão ali morrido cerca de 600 pessoas.\r\nA reconstrução pombalina, na segunda metade do século XVIII, manteve um quarteirão de edifícios que, depois da Abolição dos das Ordens Religiosas (1834), vieram a ser ocupados pela Academia de Belas Artes (1836), pela Biblioteca Nacional (1836), pelo Governo Civil de Lisboa e pela Galeria Nacional de Pintura que, a partir de 1911, daria origem ao Museu de Arte Contemporânea, atual Museu do Chiado. Foi também aí instalada a Escola Superior de Belas Artes que a partir de 1965 passou a ocupar as áreas libertadas após a transferência da Biblioteca Nacional para o novo e atual edifício, situado no Campo Grande.\r\nAs colunas do Convento de São Francisco foram utilizadas no nártex do Teatro Nacional D. Maria II (na Praça do Rossio). Atualmente, da Idade Média só subsiste a cisterna do Pátio e do Convento moderno é possível ainda observar alguma azulejaria e a escadaria situada à direita do atual edifício da Academia Nacional de Belas Artes.\r\n', 'https://pt.wikipedia.org/wiki/Convento_de_S%C3%A3o_Francisco_da_Cidade#/media/File:FBAUL.jpg', 0, 0),
(127, 0, '', 0, '', '', 0, 0, 0, 0, 0, 0, 0, '', '', 0, 0),
(128, 0, 'Edifício Sedas Nunes', 0, '1649-026', 'Raul Hestnes Ferreira e o ISCTE-Instituto Universitário de Lisboa', -9.154841, 38.748348, 0, 0, 0, 0, 0, 'Uma parte da identidade do ISCTE resulta da apropriação em etapas progressivas do espaço que ocupa atualmente. O campus em que nos encontramos foi construído em fases sucessivas, mas sempre sob a orientação do arquiteto Hestnes Ferreira.\r\nRaul Hestnes Ferreira, filho de José Gomes Ferreira e de Ingrid Hestnes, nasceu em 1931 em Lisboa, tendo falecido a 11 de fevereiro de 2018. Estudou arquitetura nas Escolas Superiores de Belas Artes em Lisboa e no Porto e, mesmo antes de terminar o curso, frequentou o Instituto de Tecnologia de Helsínquia, tomando contacto com a obra de Alvar Aalto. Em 1961, obteve uma bolsa da Fundação Calouste Gulbenkian. Realizou então o mestrado nos Estados Unidos, trabalhando com Louis Khan. Estas foram, segundo o próprio, as duas grandes influências na sua obra, para além de Keil do Amaral que o influenciou na definição da profissão de Arquiteto.\r\nPara além do atelier privado em que produziu, como tantos outros da sua geração, moradias privadas, como as casas de Albarraque e de Queijas, trabalhou para câmaras municipais e para o Estado, em particular em construções escolares.\r\nFoi aí que orientou, entre 1970 e 1975, um grupo de jovens arquitetos, no chamado gabinete da Cidade Universitária, num edifício com o cognome jocoso de Texas, situado no local do nosso parque de estacionamento.\r\nAssim, o primeiro edifício do ISCTE foi produzido num contexto de penúria de materiais e incertezas, ao mesmo tempo de tristezas e de alegrias. Destinado inicialmente à área de Psicologia e ao ISCTE, o edifício acabou por ser ocupado pelo ISCTE e pelo ICS-IUL, começando a funcionar no ano letivo de 1975/76. O ISCTE lutou por expandir o seu espaço usando todas as possibilidades que a integração na União Europeia forneceu. Assim, sempre sob a orientação do arquiteto Raul Hestnes Ferreira, nasceram seguidamente o edifício da Ala Autónoma, em 1990, o edifício do INDEG, em 1991, e o Edifício II que obteve o Prémio Valmor em 2002, um dos vários prémios e distinções que as obras de Hestnes Ferreira mereceram.\r\n', 'http://transparencias.info/imagens/dez_2010/entrevista.jpg', 100, 0),
(129, 0, 'ISCTE Pátio 1', 0, '', 'ISCTE - Instituto Universitário de Lisboa', -9.154387, 38.748889, 0, 0, 0, 0, 0, 'O atual ISCTE-Instituto Universitário de Lisboa foi criado em 1972, sob a denominação de Instituto Superior de Ciências do Trabalho e da Empresa (ISCTE), pelo decreto-Lei n.º 522/72, de 15 de dezembro, no contexto da reforma iniciada em janeiro de 1970. Pretendeu-se que o ISCTE se desenvolvesse como uma nova instituição universitária, fora do universo tradicional, introduzindo uma profunda mudança na atitude politica do ensino das Ciências Sociais. Um dos seus fundadores foi Adérito Sedas Nunes, nome atual do edifício em que nos encontramos. Quando nasceu, o ISCTE tinha basicamente duas valências: uma em Gestão e outra em Sociologia. Ao longo do tempo tem alargado o ensino e a investigação a múltiplas áreas, como a Antropologia, a História, a Ciência Política, as Tecnologias, a Arquitetura, a Psicologia, entre outras. As primeiras instalações do ISCTE foram num edifício no Campo Grande, então pertencente à Universidade Nova de Lisboa, uma moradia prolongada com edifícios prefabricados. Aí, a biblioteca funcionava num vão da escada. Seguidamente passou por uma fase de transição, estando instalado na Feira Popular de Lisboa, enquanto se ia construindo o primeiro edifício do ISCTE, já concebido pelo arquiteto Raul Hestnes Ferreira. Inicialmente foi construída apenas uma parte do atual edifício Sedas Nunes, completado seguidamente por outra parte, de modo a configurar um edifício quadrado, com um pátio interior. O ISCTE foi desde o início uma escola muito internacionalizada, pois recebeu para o quadro dos seus professores alguns exilados estrangeiros e portugueses que no exílio tinham feito os seus cursos. Atravessou fases difíceis, mas não deixou de lutar e programar o seu crescimento obtendo o reconhecimento de novos cursos, dos doutoramentos, dos Centros de Investigação e, finalmente, a sua autonomia como Instituto Universitário. Esse é um dos elementos da sua identidade. Atualmente ISCTE-Instituto Universitário de Lisboa possibilita um diversificado conjunto de formações, organizadas a partir de 3 Escolas: a Escola de Ciências Sociais e Humanas, a Escola de Gestão, a Escola de Sociologia e Políticas Públicas e a Escola de Tecnologias e Arquitetura. ', 'https://media-manager.noticiasaominuto.com/naom_560454261a938.jpg?&w=1920', 0, 0);

--
-- Índices para tabelas despejadas
--

--
-- Índices para tabela `point_of_interest`
--
ALTER TABLE `point_of_interest`
  ADD PRIMARY KEY (`point_id`);

--
-- AUTO_INCREMENT de tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `point_of_interest`
--
ALTER TABLE `point_of_interest`
  MODIFY `point_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=139;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
