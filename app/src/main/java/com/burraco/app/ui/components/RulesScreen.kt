package com.burraco.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import com.burraco.app.R
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.burraco.app.navigation.GameScreen
import com.burraco.app.ui.theme.BurracoTheme
import com.burraco.app.ui.util.adjustedModifier

private sealed class RuleContent {
    data class Title(val text: String) : RuleContent()
    data class Subtitle(val text: String) : RuleContent()
    data class Paragraph(val text: String) : RuleContent()
}

private val rulesContent = listOf(
    RuleContent.Title("Regole"),
    RuleContent.Subtitle("Introduzione"),
    RuleContent.Paragraph(
        "Il Burraco è un gioco di carte che si gioca con due mazzi di carte " +
                "francesi standard più i quattro Jolly, per un totale di 108 carte. " +
                "Lo scopo è realizzare il punteggio più alto attraverso più mani di gioco, " +
                "solitamente giocando in quattro a coppie. L'obiettivo di una mano " +
                "è \"chiudere\". Per farlo, una coppia deve aver preso il proprio \"pozzetto\", o \"mazzetto\", " +
                "e aver realizzato almeno un \"Burraco\" e infine esaurito tutte le carte in " +
                "mano, terminando con uno scarto."
    ),
    RuleContent.Subtitle("Svolgimento"),
    RuleContent.Paragraph(
        "All'inizio della partita, il mazziere distribuisce 11 carte a " +
                "ciascun giocatore e prepara due \"pozzetti\", o \"mazzetti\", da 11 carte ciascuno. " +
                "Le carte rimanenti formano il \"Tallone\" (mazzo coperto) e la prima " +
                "carta scoperta avvia il \"Monte degli Scarti\". Il turno di un giocatore " +
                "segue tre fasi. Inizia obbligatoriamente pescando una carta dal Tallone o " +
                "raccogliendo l'intero Monte degli Scarti. Successivamente, può " +
                "facoltativamente calare carte, che sono \"Combinazioni\" (almeno tre " +
                "carte dello stesso valore ma semi diversi) o \"Sequenze\" (almeno tre " +
                "carte in scala dello stesso seme). Infine, deve obbligatoriamente " +
                "scartare una carta nel Monte degli Scarti per concludere il turno."
    ),
    RuleContent.Subtitle("Matte e Burraco"),
    RuleContent.Paragraph(
        "Le \"Matte\", ovvero i Jolly e tutti i 2 (chiamati \"Pinelle\"), " +
                "possono sostituire qualsiasi carta, ma se ne può usare al massimo una " +
                "per ogni scala o combinazione. Esiste un'eccezione: se una Pinella (il 2) " +
                "viene usata nella sua posizione naturale in una scala (ad esempio, un 2 di cuori " +
                "in una sequenza A-2-3 di cuori), quella carta non conta come Matta. " +
                "Di conseguenza, è possibile aggiungere un'altra Matta (come un Jolly) " +
                "a quella stessa scala."
    ),
    RuleContent.Paragraph(
        "Un \"Burraco\" è un gioco composto da almeno sette carte; " +
                "è \"Pulito\" se non contiene Matte (vale 200 punti) o \"Sporco\" se ne " +
                "contiene una (vale 100 punti)."
    ),
    RuleContent.Subtitle("Chiusura"),
    RuleContent.Paragraph(
        "Quando un giocatore esaurisce le 11 carte iniziali, prende il " +
                "\"pozzetto\", o \"mazzetto\". Se lo fa terminando con uno scarto, potrà giocare le nuove " +
                "carte solo al turno successivo (o dopo il turno del compagno, nel gioco " +
                "a coppie). Se invece cala tutte le carte che ha in mano senza scartare, " +
                "prende il pozzetto \"al volo\" e può giocarlo immediatamente nello stesso " +
                "turno. La chiusura della smazzata (che vale 100 punti) è possibile solo " +
                "scartando un'ultima carta che non sia una Matta."
    ),
    RuleContent.Subtitle("Punteggi"),
    RuleContent.Paragraph(
        "Il punteggio finale della smazzata si calcola sommando i punti " +
                "\"onore\" (chiusura e bonus Burraco) e il valore di tutte le carte " +
                "calate sul tavolo. Da questo totale si sottraggono i punti delle carte " +
                "rimaste in mano e una penalità di 100 punti se la coppia non è " +
                "riuscita a prendere il pozzetto."
    ),
    RuleContent.Paragraph(
        "Il valore delle singole carte, usato sia per i punti positivi (calati) " +
                "che per quelli negativi (in mano), è il seguente: " +
                "Jolly 30 punti; Pinella (2) 20 punti; Asso 15 punti; " +
                "Re, Donna, Fante, 10, 9 e 8 valgono 10 punti; le carte dal 7 al 3 valgono 5 punti."
    ),
    RuleContent.Paragraph(
        "Il bonus per i Burrachi realizzati è il seguente: " +
                "un \"Burraco Pulito\" (almeno sette carte senza Matte) vale 200 punti; " +
                "un \"Burraco Semipulito\" (almeno otto carte con una Matta, ma con sette carte pulite consecutive al suo interno) vale 150 punti; " +
                "un \"Burraco Sporco\" (almeno sette carte con l'uso di una Matta) vale 100 punti. " +
                "Questi punti si sommano al valore delle singole carte che li compongono."
    ),
    RuleContent.Subtitle("Varianti"),
    RuleContent.Paragraph(
        "Esistono anche varianti. Nel gioco a 2, le regole sono identiche " +
                "ma la strategia è individuale. Nel gioco a 3, la preparazione è " +
                "asimmetrica: si forma un \"pozzetto\", o \"mazzetto\", da 18 carte e uno da 11. Il primo " +
                "giocatore che esaurisce la mano prende il pozzetto da 18 e gioca da " +
                "solo contro gli altri due, che diventano una coppia, per la mano corrente, e prenderanno poi " +
                "il pozzetto da 11. I punteggi vengono divisi a metà per la coppia, " +
                "mentre il giocatore singolo li tiene per sé."
    )
)


@Composable
fun RulesScreen(
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit
) {
    val columnState = rememberLazyListState()

    Column (
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .weight(0.96f)
                .fillMaxWidth()
        ) {
            LazyColumn(
                state = columnState,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
            ) {
                itemsIndexed(rulesContent) { index, ruleContent ->
                    when (ruleContent) {
                        is RuleContent.Title -> {
                            Text(
                                modifier = Modifier
                                    .padding(top = 16.dp, bottom = 16.dp)
                                    .fillMaxWidth(),
                                text = ruleContent.text,
                                style = MaterialTheme.typography.titleLarge,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                                textAlign = TextAlign.Center
                            )
                        }

                        is RuleContent.Subtitle -> {
                            SubTitle(text = ruleContent.text)
                        }

                        is RuleContent.Paragraph -> {
                            SimpleText(text = ruleContent.text)
                            Spacer(modifier = Modifier.height(5.dp))
                        }
                    }
                    if(index == 0){
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                modifier = Modifier.fillMaxWidth(0.8f),
                                painter = painterResource(id = R.drawable.heart_ruler),
                                contentScale = ContentScale.Crop,
                                contentDescription = "Illustrazione di un cuore che legge una pergamena",
                            )
                        }
                    }
                }
            }

            // I gradienti ora si sovrappongono correttamente alla LazyColumn
            if (columnState.canScrollBackward) {
                RuleGradient(modifier = Modifier.align(Alignment.TopCenter), reversed = true)
            }
            if (columnState.canScrollForward) {
                RuleGradient(modifier = Modifier.align(Alignment.BottomCenter))
            }
        }

        Spacer(modifier = Modifier.weight(0.02f))
        BurracoButton(
            onClick = { onNavigate(GameScreen.Match.route) },
            text = "Torna alla Home",
            large = true
        )
        Spacer(modifier = Modifier.weight(0.02f))
    }
}

@Composable
fun SubTitle(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        modifier = modifier.padding(top = 10.dp, bottom = 5.dp),
        text = text.replaceFirstChar { it.uppercase() },
        style = MaterialTheme.typography.titleMedium,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        textAlign = TextAlign.Start
    )
}

@Composable
fun SimpleText(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        modifier = modifier.padding(top = 5.dp, bottom = 3.dp),
        text = text.replaceFirstChar { it.uppercase() },
        style = MaterialTheme.typography.labelSmall,
        fontSize = 18.sp,
        textAlign = TextAlign.Start
    )
}

@Composable
fun RuleGradient(
    modifier: Modifier = Modifier,
    reversed: Boolean = false
){
    val gradient = if (reversed) {
        Brush.verticalGradient(
            colors = listOf(
                MaterialTheme.colorScheme.background,
                MaterialTheme.colorScheme.background,
                Color.Transparent,
            ), // Da colore a sfondo
        )
    }
    else {
        Brush.verticalGradient(
            colors = listOf(
                Color.Transparent,
                MaterialTheme.colorScheme.background,
                MaterialTheme.colorScheme.background,
            ),
        )
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(
                brush = gradient
            )
    )
}

@Preview(
    showBackground = true, showSystemUi = true,
    device = "spec:parent=pixel_5,navigation=buttons"
)
@Composable
fun RuleTest() {
    BurracoTheme {
        RulesScreen(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .adjustedModifier(),
            onNavigate = {}
        )

    }
}